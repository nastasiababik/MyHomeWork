package com.example.jsonplaceholder;

import io.restassured.response.Response;

import java.util.Map;

public class RequestFactory {

    public static ApiRequest createRequest(String method,
                                           String url,
                                           Map<String, Object> body
                                        ){

        if("GET".equalsIgnoreCase(method)){
            return new GetRequest(url);
        } else if("POST".equalsIgnoreCase(method) && body != null){
            return new PostRequest(url, body);
        } else if("POST".equalsIgnoreCase(method) && body == null){
            return new PostRequest(url);
        }

        throw new IllegalArgumentException("Указан неизвестный http-метод");

    }

}
