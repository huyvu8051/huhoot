package com.huhoot.config;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
class CustomRestResponse implements Serializable {
    private int status;
    private String message;
    private Object data;
}