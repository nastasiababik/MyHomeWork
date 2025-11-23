package com.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RestTests {

    private StudentData student;
    private int id;

    @BeforeEach
    void setUp() {
        student = new StudentData(1,"Вася", List.of(2, 3, 4));

        RestAssured.given()
                .baseUri("http://localhost:8888")
                .basePath("/student")
                .contentType(ContentType.JSON)
                .body(student)
                .post();
    }

    /**
     * Отдельный тест на каждое требование к endpoint'у
     */

    @DisplayName("Пример теста на простой гет из презентации")
    @Test
    public void myTest(){
        RestAssured.given()
                .baseUri("http://localhost:8888/student/100000")
                .contentType(ContentType.JSON)
                .when().get().then().statusCode(404);
    }

    @DisplayName("Пример теста на простой гет из презентации")
    @Test
    public void myTestBody(){
        int id = 1;
        RestAssured.given()
                .baseUri("http://localhost:8888/student/"+id)
                .contentType(ContentType.JSON)
                .when().get().then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", Matchers.equalTo(id));
}
}