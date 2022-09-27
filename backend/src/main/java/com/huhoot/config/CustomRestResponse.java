package com.huhoot.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Builder
public class CustomRestResponse<T> implements Serializable {
    private int status;
    private String message;
    @Setter
    private T data;
}