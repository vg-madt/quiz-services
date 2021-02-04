package com.quiz.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    private String student;
    private String attemptedOn;
    private Float score;
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
	}
	public String getAttemptedOn() {
		return attemptedOn;
	}
	public void setAttemptedOn(String attemptedOn) {
		this.attemptedOn = attemptedOn;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	

}
