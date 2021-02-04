package com.quiz.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    private String question;

    private List<String> choices;

    private Answer answer;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	

}
