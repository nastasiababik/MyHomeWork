package com.example.jsonplaceholder;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetRequest implements ApiRequest{
    private String url;

    public GetRequest(String url) {
        this.url = url;
    }

    @Override
    public Response sendRequest(){
        return RestAssured.get(url);
    }

}
