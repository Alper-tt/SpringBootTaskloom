package com.taskloom.services;

import com.taskloom.entity.TaskEntity;
import com.taskloom.model.TaskStatus;
import com.taskloom.model.request.TaskCreateRequest;
import com.taskloom.model.request.TaskStatusUpdate;
import com.taskloom.model.request.TaskUpdateRequest;
import com.taskloom.model.response.TaskResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;

public class TaskTestService extends BaseTest {

    public Response createTask(TaskCreateRequest taskCreateRequest) {
        Response response = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(taskCreateRequest)
                .when()
                .post("/tasks");

        //TaskResponse taskResponse = response
        //        .then()
        //        .spec(responseSpec)
        //        .statusCode(HttpStatus.CREATED.value())
        //        .extract().as(TaskResponse.class);
//
        //Assertions.assertNotNull(taskResponse);
        //Assertions.assertEquals(taskCreateRequest.getTitle(), taskResponse.getTitle());
        //Assertions.assertEquals(taskCreateRequest.getDescription(), taskResponse.getDescription());
        //if (taskCreateRequest.getStatus() == null) {
        //    Assertions.assertEquals(TaskStatus.TODO, taskResponse.getStatus());
        //} else {
        //    Assertions.assertEquals(taskCreateRequest.getStatus(), taskResponse.getStatus());
        //}
        return response;
    }

    public Response getAllTasks() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/tasks");

        //response.then()
        //        .spec(responseSpec)
        //        .statusCode(HttpStatus.OK.value())
        //        .extract().as(List.class);
//
        //Assertions.assertEquals(List.class, response.getBody().getClass());

        return response;
    }

    public Response getTaskById(Integer id) {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/tasks/" + id);

        //TaskEntity taskEntity = response
        //        .then()
        //        .spec(responseSpec)
        //        .statusCode(HttpStatus.OK.value())
        //        .extract().as(TaskEntity.class);
//
        //Assertions.assertEquals(taskEntity.getId(), id);

        return response;
    }

    public Response deleteTaskById(Integer id){
        Response response = given()
                .spec(requestSpec)
                .when()
                .delete("/tasks/" + id);

        //response.then()
        //        .spec(responseSpec)
        //        .statusCode(HttpStatus.CREATED.value());
//
        //Assertions.assertEquals(response.getBody().toString(), id + " was deleted");

        return response;
    }

    public Response updateTaskStatus(Integer id, TaskStatusUpdate taskStatusUpdate) {
        Response response = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(taskStatusUpdate)
                .when()
                .patch("/tasks/"+id+"/status");

        //TaskResponse taskResponse = response
        //        .then()
        //        .spec(responseSpec)
        //        .statusCode(HttpStatus.ACCEPTED.value())
        //        .extract().as(TaskResponse.class);
//
        //Assertions.assertEquals(taskResponse.getStatus(), taskStatusUpdate.getTaskStatus());
        //Assertions.assertEquals(id, taskResponse.getId());
        return response;
    }

    public Response updateTask(Integer id, TaskUpdateRequest taskUpdateRequest) {
        Response response = given()
                .contentType(ContentType.JSON)
                .spec(requestSpec)
                .body(taskUpdateRequest)
                .when()
                .put("tasks/" + id);

        //TaskResponse taskResponse = response
        //        .then()
        //        .spec(responseSpec)
        //        .statusCode(HttpStatus.ACCEPTED.value())
        //        .extract().as(TaskResponse.class);
//
        //Assertions.assertEquals(id, taskResponse.getId());
        //Assertions.assertEquals(taskUpdateRequest.getTitle(), taskResponse.getTitle());
        //Assertions.assertEquals(taskUpdateRequest.getDescription(), taskResponse.getDescription());
        //Assertions.assertEquals(taskUpdateRequest.getStatus(), taskResponse.getStatus());

        return response;
    }


}
