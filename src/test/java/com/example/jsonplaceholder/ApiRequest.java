package com.example.jsonplaceholder;

import io.restassured.response.Response;

public interface ApiRequest {
    Response sendRequest();
}
