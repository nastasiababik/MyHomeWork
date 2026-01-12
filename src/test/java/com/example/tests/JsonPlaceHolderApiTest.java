package com.example.tests;

import com.example.jsonplaceholder.ApiRequest;
import com.example.jsonplaceholder.RequestFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Objects;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonPlaceHolderApiTest {

    @BeforeAll
    static void setUp(){
        baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    @DisplayName("Проверить, что метод GET вернет хотя бы одного пользователя")
    void testGetAllUsers(){

        RestAssured.when().get("/users")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].name", notNullValue());
    }

    @Test
    @DisplayName("Post /post: Создать пост пользователя c id 1")
    void testCreateUsersPost(){

        HashMap<String, Object> bodyData = new HashMap<>();
        bodyData.put("title", "My first post");
        bodyData.put("body", "Hello, I'm use JsonPlaceHolder!");
        bodyData.put("userId", 1);

        RestAssured.given()
                        .contentType(ContentType.JSON)
                        .body(bodyData)
                .when()
                        .post("/posts")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", notNullValue(), "title",
                        equalTo("My first post"),
                        "body", equalTo("Hello, I'm use JsonPlaceHolder!"),
                        "userId", equalTo(1));

    }

    @Test
    @DisplayName("Отправить POST-запрос используя фабрику")
    void testPostRequestUsingRequestFactory(){
        HashMap<String, Object> bodyData = new HashMap<>();
        bodyData.put("title", "My second post");
        bodyData.put("body", "I love java and cats!");
        bodyData.put("userId", 1);

        HashMap<String, Object> expectedData = new HashMap<>(bodyData);
        expectedData.put("id", 101);


        ApiRequest createUserRequest = RequestFactory.createRequest("POST",
                                                                 "https://jsonplaceholder.typicode.com/posts", bodyData);

        Response response = createUserRequest.sendRequest();
        response.then().log().all();
        assertEquals(201, response.getStatusCode());
        assertEquals(expectedData, response.jsonPath().getMap(""), "Body респонса отличается от ожидаемого");
    }


}
