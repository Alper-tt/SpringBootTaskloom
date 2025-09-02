package com.taskloom.services;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Arrays;

import static org.hamcrest.Matchers.lessThan;

public class BaseTest {
    RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .setAccept("application/json")
            .addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()))
            .build();
    ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectResponseTime(lessThan(2000L))
            .build();
}
