package com.quiz.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Score> scores;

    public void add(@NotNull final Score score) {
        if (scores == null) {
            scores = new ArrayList<>();
        }
        scores.add(score);
    }

}
