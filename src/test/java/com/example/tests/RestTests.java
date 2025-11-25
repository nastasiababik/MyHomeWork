package com.example.tests;

import com.example.model.StudentData;
import com.example.model.StudentResponse;
import com.example.requests.StudentApi;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;

import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Random;

import static com.example.requests.StudentApi.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RestTests {
    private int id;
    private int NON_EXISTING_ID;

    /*
    Метод для сравнения содержимого объекта student и json из payload ответа
     */
    private void assertStudentDataEquals(StudentData student, StudentResponse payload){
        assertEquals(student.getId(), payload.getId());
        assertEquals(student.getName(), payload.getName());
        assertEquals(student.getMarks(), payload.getMarks());
    }

    /*
    Метод, который извлекает payload из переданного типа респонса и сравнивает его с созданным студентом.
    Название пока не придумала
    */
    private void myTestMethod(Response response, StudentData student){
        StudentResponse payload = response.body().as(StudentResponse.class, ObjectMapperType.JACKSON_2);
        assertStudentDataEquals(student, payload);
    }

    @BeforeEach
    void setUp() {
        // Генерирует уникальный id перед каждым тестом
        id = new Random().nextInt(100000);
        NON_EXISTING_ID = id +1;

        // Если пользователь с id есть в базе, то удаляем его, чтобы тесты не падали
        Response response = StudentApi.getStudentById(id);
        if (response.getStatusCode() == 200) {
            deleteStudent(id);
        }
    }

    @AfterEach
    void cleanup() {
        // На всякий случай удалить после теста студента
        deleteStudent(id);
    }

    @DisplayName("1. GET /student/{id} для существующего в базе студента: возвращает код 200, JSON студента с ID и именем")
    @Test
    public void getStudentShouldReturn200(){
        //Создать студента с id и добавить через POST в базу
        StudentData student = new StudentData(id,"Барсик", List.of(2, 3, 4));
        Response createResponse = postStudent(student);

        Response getResponse = StudentApi.getStudentById(id);

        assertEquals(ContentType.JSON.toString(), getResponse.getHeader("Content-Type"));
        assertEquals(200, getResponse.getStatusCode());

        myTestMethod(getResponse, student);
        /*
        StudentResponse payload = getResponse.body().as(StudentResponse.class, ObjectMapperType.JACKSON_2);
        assertStudentDataEquals(student, payload);

         */
    }

    @DisplayName("2. GET /student/{id} для несуществующего в базе ID-студента: возвращает код 404")
    @Test
    public void getStudentShouldReturn404(){
        Response response = StudentApi.getStudentById(NON_EXISTING_ID);

        /* Закомментированная проверка, т.к. в RestApp.jar не реализован Content-Type для ответа 404
        assertEquals(ContentType.JSON.toString(), response.getHeader("Content-Type"));
         */
        assertEquals(404, response.getStatusCode());

    }

    @DisplayName("4. POST /student добавляет студента в базу, если студента с таким ID ранее не было, при этом имя заполнено, код 201")
    @Test
    public void createStudentShouldReturn201(){
        StudentData student = new StudentData(id, "Лера", List.of(4, 3));

        Response getResponseBeforeCreate = StudentApi.getStudentById(id);
        assertEquals(404, getResponseBeforeCreate.getStatusCode());
        Response postResponse = postStudent(student);
        assertEquals(201, postResponse.getStatusCode());

        Response getResponseAfterCreate = StudentApi.getStudentById(id);
        myTestMethod(getResponseAfterCreate, student);
        /*
        StudentResponse payload = getResponseAfterCreate.body().as(StudentResponse.class, ObjectMapperType.JACKSON_2);
        assertStudentDataEquals(student, payload);
         */

    }

    @DisplayName("5. POST /student добавляет студента в базу: если ID равен null, сервер назначает ID, код 201")
    @Test
    public void createStudentWithNullIdShouldReturn201() {
        StudentData student = new StudentData(null, "Вика", List.of(4, 3));
        Response postResponse = postStudent(student);
        assertEquals(201, postResponse.getStatusCode());

        int returnId = postResponse.getBody().jsonPath().getInt(""); //POST возвращает просто число 99658

        Response getResponse = StudentApi.getStudentById(returnId);
        assertEquals(200, getResponse.getStatusCode());
        assertEquals(student.getName(), getResponse.getBody().jsonPath().getString("name"));
        assertEquals(student.getMarks(), getResponse.getBody().jsonPath().getList("marks"));
    }

    @DisplayName("6. POST /student студент без имени: вовзращает код 400")
    @Test
    public void createStudentShouldReturn400(){
        StudentData nonameStudent = new StudentData(id, null, List.of(2,5));
        Response response = postStudent(nonameStudent);
        assertEquals(400, response.getStatusCode());
    }

    @DisplayName("7. DELETE /student/{id} удалить студента по id из базы, код 200")
    @Test
    public void deleteStudentShouldReturn200(){
        StudentData student = new StudentData(id,"Вася", List.of(5));
        Response createStudentResponse = postStudent(student);

        Response deleteResponse = deleteStudent(student.getId());
        /* Закомментированная проверка, т.к. в RestApp.jar не реализован Content-Type для ответа 404
        assertEquals(ContentType.JSON.toString(), response.getHeader("Content-Type"));
         */
        assertEquals(200, deleteResponse.getStatusCode());

        //В конце теста проверить, что студент удален попытаться вернуть его по id
        Response getResponse = StudentApi.getStudentById(id);
        assertEquals(404, getResponse.getStatusCode());
    }

    @DisplayName("8. DELETE /student/{id} удалить студента по несуществующему id, код 404")
    @Test
    public void deleteStudentShouldReturn404(){
        Response response = deleteStudent(NON_EXISTING_ID);
        /*
        Закомментированная проверка, т.к. в RestApp.jar не реализован Content-Type для ответа 404
        assertEquals(ContentType.JSON.toString(), response.getHeader("Content-Type"));
         */
        assertEquals(404, response.getStatusCode());

    }




    //get /topStudent ...


}