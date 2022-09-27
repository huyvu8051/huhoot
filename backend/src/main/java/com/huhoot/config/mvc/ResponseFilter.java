package com.huhoot.config.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huhoot.config.CustomRestResponse;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
public class ResponseFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ResponseWrapper responseWrapper = new ResponseWrapper(response);

        filterChain.doFilter(request, responseWrapper);

        byte[] dataStream = responseWrapper.getDataStream();

        String responseContent = new String(dataStream);


        int status = response.getStatus();

        byte[] responseToSend;

        CustomRestResponse.CustomRestResponseBuilder builder = CustomRestResponse.builder();

        if(HttpStatus.valueOf(status).isError()){

            response.setStatus(HttpStatus.OK.value());
            builder.data(null);
            builder.message(responseContent);

        }else {
            JSONParser parser = new JSONParser(responseContent);
            try {
                Object json = parser.parse();

                builder.data(json);
                builder.message(HttpStatus.OK.getReasonPhrase());

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }


        responseToSend = restResponseBytes(builder.status(status).build());

        response.getOutputStream().write(responseToSend);


        response.setStatus(200);
    }


    private byte[] restResponseBytes(CustomRestResponse response) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(response);
        return serialized.getBytes();
    }
}