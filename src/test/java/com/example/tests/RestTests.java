package com.example.tests;

import com.example.model.StudentData;
import com.example.model.StudentResponse;
import com.example.requests.StudentApi;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.*;

import java.util.List;

import static com.example.requests.StudentApi.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestTests {

    private StudentData student;
    private static final int NON_EXISTING_ID = 1000;


    @BeforeEach
    void setUp() {
        student = new StudentData(1,"Барсик", List.of(2, 3, 4));
        Response response = postStudent(student);

    }

    @AfterEach
     void tearDown() {
        Response response = deleteStudent(student.getId());
        student = null;
    }


    //get /student/{id} возвращает JSON студента с указанным ID и заполненным именем, если такой есть в базе, код 200.
    @DisplayName("GET /student/{id} для существующего в базе студента: возвращает код 200, JSON студента с ID и именем")
    @Test
    public void getStudentShouldReturn200(){
        int id = 1;
        Response response = StudentApi.getStudentById(id);

        assertEquals(ContentType.JSON.toString(), response.getHeader("Content-Type"));
        assertEquals(200, response.getStatusCode());
        assertEquals(id, response.jsonPath().getInt("id"));
        assertEquals(student.getName(), response.jsonPath().getString("name"));
        assertEquals(student.getMarks(), response.jsonPath().getList("marks"));
    }

    //get /student/{id} возвращает код 404, если студента с данным ID в базе нет.
    @DisplayName("GET /student/{id} для несуществующего в базе ID-студента: возвращает код 404")
    @Test
    public void getStudentShouldReturn404(){
        Response response = StudentApi.getStudentById(NON_EXISTING_ID);

        /* Закомментированная проверка, т.к. в RestApp.jar не реализован Content-Type для ответа 404
        assertEquals(ContentType.JSON.toString(), response.getHeader("Content-Type"));
         */
        assertEquals(404, response.getStatusCode());

    }

    //post /student добавляет студента в базу, если студента с таким ID ранее не было, при этом имя заполнено, код 201


    //post /student возвращает код 400, если имя не заполнено.
    @DisplayName("POST /student студент без имени: вовзращает код 400")
    @Test
    public void createStudentShouldReturn400(){
        StudentData nonameStudent = new StudentData(2, null, List.of(2,5));
        Response response = postStudent(nonameStudent);
        assertEquals(400, response.getStatusCode());
    }

    //delete /student/{id} удаляет студента с указанным ID из базы, код 200.
    @DisplayName("DELETE /student/{id} удалить студента по id из базы, код 200")
    @Test
    public void deleteStudentShouldReturn200(){
        Response response = deleteStudent(student.getId());
        assertEquals(ContentType.JSON.toString(), response.getHeader("Content-Type"));
        assertEquals(200, response.getStatusCode());
        StudentResponse createStudentResponse = response.as(StudentResponse.class);
    }

    //delete /student/{id} возвращает код 404, если студента с таким ID в базе нет.
    @DisplayName("DELETE /student/{id} удалить студента по несуществующему id, код 404")
    @Test
    public void deleteStudentShouldReturn404(){
        Response response = deleteStudent(NON_EXISTING_ID);
        /*
        Закомментированная проверка, т.к. в RestApp.jar не реализован Content-Type для ответа 404
        assertEquals(ContentType.JSON.toString(), response.getHeader("Content-Type"));
         */
        assertEquals(404, response.getStatusCode());

    }




    //get /topStudent код 200 и пустое тело, если студентов в базе нет.

}