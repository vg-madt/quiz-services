package com.quiz.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class ResponseHandler extends AbstractHandler {

    public <T> String serialize(final T t) {
        try {
            return getMapper().writeValueAsString(t);
        } catch (JsonProcessingException e) {
            return "{\"message\": \"" + e.getMessage() + "\", \"code\": 500}";
        }
    }

}
