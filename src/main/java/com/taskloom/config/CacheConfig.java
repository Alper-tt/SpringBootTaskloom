package com.taskloom.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        ObjectMapper typedMapper = new ObjectMapper();
        typedMapper.registerModule(new JavaTimeModule());
        typedMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        typedMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );

        ObjectMapper plainMapper = new ObjectMapper();
        plainMapper.registerModule(new JavaTimeModule());
        plainMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        var keySerializer = new StringRedisSerializer();

        var typedSerializer = new GenericJackson2JsonRedisSerializer(typedMapper);
        var listSerializer = new GenericJackson2JsonRedisSerializer(plainMapper);

        RedisCacheConfiguration typedCfg = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(typedSerializer))
                .entryTtl(Duration.ofMinutes(60));

        RedisCacheConfiguration listCfg = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(listSerializer))
                .entryTtl(Duration.ofMinutes(15));

        Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
        cacheConfigs.put("task:by-id", typedCfg);
        cacheConfigs.put("task:all", listCfg);
        cacheConfigs.put("task:pages", listCfg);
        cacheConfigs.put("user:by-id", typedCfg);

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(listCfg)
                .withInitialCacheConfigurations(cacheConfigs)
                .build();
    }
}
