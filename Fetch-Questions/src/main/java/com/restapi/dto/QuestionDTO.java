package com.restapi.dto;

public class QuestionDTO {
	private Long questionId;
	private String question;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long ques) {
		this.questionId = ques;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

}
