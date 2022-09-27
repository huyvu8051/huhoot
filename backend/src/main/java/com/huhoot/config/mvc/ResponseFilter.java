package com.huhoot.config.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huhoot.config.CustomRestResponse;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
public class ResponseFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);

        chain.doFilter(request, responseWrapper);

        String responseContent = new String(responseWrapper.getDataStream());

        CustomRestResponse fullResponse = CustomRestResponse.builder()
                .data(responseContent)
                .build();

        byte[] responseToSend = restResponseBytes(fullResponse);

        response.getOutputStream().write(responseToSend);

    }

    @Override
    public void destroy() {
    }

    private byte[] restResponseBytes(CustomRestResponse response) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(response);
        return serialized.getBytes();
    }
}