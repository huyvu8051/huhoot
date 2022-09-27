package com.huhoot.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Builder
public class CustomRestResponse implements Serializable {
    private int status;
    private String message;
    private Object data;
}