package com.quiz.util;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class RequestHandler extends AbstractHandler {

    public <T> T deserialize(final Class<T> type, final String body) {
        try {
            return getMapper().readValue(body, type);
        } catch (final IOException e) {
            return null;
        }
    }

}
