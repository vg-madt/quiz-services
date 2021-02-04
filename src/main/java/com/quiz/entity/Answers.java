package com.quiz.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Answers implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Question> questions;
    private List<Answer> answers;

    public float evaluate() {
        final int size = questions.size();
        int correct = 0;
        for (int i = 0; i < size; i++) {
            final Answer correctAnswer = questions.get(i).getAnswer();
            final Answer actualAnswer = answers.get(i);
            if (correctAnswer.equals(actualAnswer)) {
                correct++;
            }
        }
        return correct / size;
    }

}
