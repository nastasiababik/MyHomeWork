package com.example.utils;

import lombok.Getter;

@Getter
public enum EndpointPath {
    STUDENT("student"),
    TOP_STUDENT("topStudent");

    private final String path;

    EndpointPath(String path) {
        this.path = path;
    }

}
