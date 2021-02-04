package com.quiz.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Question> questions;

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}
