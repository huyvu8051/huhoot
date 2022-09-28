package com.huhoot.config.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Wrap body response to a Wrapped Sealed object.
 *
 * @Author HuyVu
 * @CreatedDate 28/9/2022
 */
@Slf4j
@RestControllerAdvice
public class PreHandleBodySuccessResponse implements ResponseBodyAdvice<Object> {

    /**
     * If the returned Object from RestController is Implementation of com.huhoot.config.mvc.ICustomResponse -> do wrap.
     *
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> methodReturnType = returnType.getMethod().getReturnType();
        return ICustomBodyResponse.class.isAssignableFrom(methodReturnType);
    }

    /**
     * Wrap with HTTPStatus.OK.
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.OK);
        return CustomRestResponse.builder()
                .status(HttpStatus.OK)
                .data(body)
                .build();

    }
}
