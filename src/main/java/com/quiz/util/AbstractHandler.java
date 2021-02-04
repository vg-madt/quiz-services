package com.quiz.util;

import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractHandler {

    private final ObjectMapper mapper;

    protected AbstractHandler() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public <T> T deserialize(final Class<T> type, final Reader reader) {
        try {
            return getMapper().readValue(reader, type);
        } catch (final IOException e) {
            return null;
        }
    }

}
