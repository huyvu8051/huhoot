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

        int status = response.getStatus();

        byte[] responseToSend;

        if(HttpStatus.valueOf(status).isError()){


        }else {

            String responseContent = new String(dataStream);

            JSONParser parser = new JSONParser(responseContent);
            try {
                Object json = parser.parse();
                CustomRestResponse fullResponse = CustomRestResponse.builder()
                        .status(response.getStatus())
                        .data(json)
                        .build();

                responseToSend = restResponseBytes(fullResponse);

                response.getOutputStream().write(responseToSend);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }





    }


    private byte[] restResponseBytes(CustomRestResponse response) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(response);
        return serialized.getBytes();
    }
}