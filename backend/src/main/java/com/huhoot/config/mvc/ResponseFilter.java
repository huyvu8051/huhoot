package com.huhoot.config.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huhoot.config.CustomRestResponse;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;


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

        byte[] dataStream = responseWrapper.getDataStream();

        String responseContent = new String(dataStream);

        JSONParser parser = new JSONParser(responseContent);
        try {
            Object json = parser.parse();
            CustomRestResponse fullResponse = CustomRestResponse.builder()
                    .status(((HttpServletResponse) response).getStatus())

                    .data(json)
                    .build();

            byte[] responseToSend = restResponseBytes(fullResponse);

            response.getOutputStream().write(responseToSend);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    public void destroy() {
    }

    private byte[] restResponseBytes(CustomRestResponse response) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(response);
        return serialized.getBytes();
    }
}