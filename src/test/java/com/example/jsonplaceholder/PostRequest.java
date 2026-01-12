package com.example.jsonplaceholder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class PostRequest implements ApiRequest{
    private String url;
    private Map<String, Object> body;

    public PostRequest(String url) {
        this.url = url;
        this.body=null;
    }

    public PostRequest(String url, Map<String, Object> body) {
        this.url = url;
        this.body = body;
    }

    @Override
    public Response sendRequest(){
        RequestSpecification request = given()
                        .log().all()  //добавляем логирование
                        .contentType(ContentType.JSON);

        //Добавляем тело, если передано
        if(body != null){
            request.body(body);
        }

        return request.post(url);
    }

}
