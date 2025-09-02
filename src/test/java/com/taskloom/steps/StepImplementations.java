package com.taskloom.steps;

import com.taskloom.model.TaskStatus;
import com.taskloom.model.request.TaskCreateRequest;
import com.taskloom.model.request.TaskStatusUpdate;
import com.taskloom.model.request.TaskUpdateRequest;
import com.taskloom.services.TaskTestService;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class StepImplementations {

    Response taskResponse;
    TaskTestService taskTestService = new TaskTestService();

    @Step("Create task that <table>")
    public void createTask(Table table) {
        TaskCreateRequest createRequest = new TaskCreateRequest();

        createRequest.setTitle(table.getColumnValues("title").get(0));
        createRequest.setDescription(table.getColumnValues("description").get(0));
        if (!table.getColumnValues("status").isEmpty()){
            createRequest.setStatus(TaskStatus.valueOf(table.getColumnValues("status").get(0)));
        }
        taskResponse = taskTestService.createTask(createRequest);
    }

    @Step("Response status code should be <statusCode>")
    public void checkStatusCode(Integer statusCode) {
        Assertions.assertEquals(statusCode, taskResponse.getStatusCode());
    }

    @Step("Response type should be <type>")
    public void checkResponseType(String type) {
    }

    @Step("Task <field> should be <value>")
    public void checkField(String field, String value) {
        Assertions.assertEquals(value, taskResponse.then().extract().path(field).toString());
    }

    @Step("Delete task by id <id>")
    public void deleteTask(Integer id){
        taskResponse = taskTestService.deleteTaskById(id);
    }

    @Step("Update task that <table>")
    public void updateTask(Table table) {
        TaskUpdateRequest updateRequest = new TaskUpdateRequest();

        updateRequest.setTitle(table.getColumnValues("title").get(0));
        updateRequest.setDescription(table.getColumnValues("description").get(0));
        updateRequest.setStatus(TaskStatus.valueOf(table.getColumnValues("status").get(0)));
        taskResponse = taskTestService.updateTask(Integer.parseInt(table.getColumnValues("id").get(0)), updateRequest);
    }

    @Step("Get task by id <id>")
    public void getTaskById(Integer id){
        taskResponse = taskTestService.getTaskById(id);
    }

    @Step("Get all tasks")
    public void getAllTasks(){
        taskResponse = taskTestService.getAllTasks();
    }

    @Step("Patch task by id <table>")
    public void changeTaskStatusById(Table table){
        TaskStatusUpdate taskStatusUpdate = new TaskStatusUpdate();
        taskStatusUpdate.setTaskStatus(TaskStatus.valueOf(table.getColumnValues("status").get(0)));

        taskResponse = taskTestService.updateTaskStatus(Integer.parseInt(table.getColumnValues("id").get(0)), taskStatusUpdate);
    }
}
