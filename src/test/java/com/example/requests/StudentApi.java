package com.example.requests;

import com.example.model.StudentData;
import com.example.model.StudentResponse;
import com.example.utils.EndpointPath;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.example.utils.EndpointPath.*;
import static io.restassured.RestAssured.given;


public class StudentApi {
    private static final String BASE_URL = "http://localhost:8888/";

    // Вынесла инфу по урлу, ендпоинту и параметру в отдельный метод. Взято из книжки Тестирование web-Api
    public static Response getStudentById(int id){
        return given()
                .contentType(ContentType.JSON)
                .get(BASE_URL + EndpointPath.STUDENT.getPath() + "/" +id);
    }

    public static Response postStudent(StudentData student){
        return given()
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post(BASE_URL + EndpointPath.STUDENT.getPath());
    }

    public static Response deleteStudent(int id){
        return given()
                .contentType(ContentType.JSON)
                .delete(BASE_URL + EndpointPath.STUDENT.getPath() + "/" +id);
    }

    public static Response topStudent(StudentData student){
        return given()
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .get(BASE_URL + TOP_STUDENT.getPath());
    }


}
