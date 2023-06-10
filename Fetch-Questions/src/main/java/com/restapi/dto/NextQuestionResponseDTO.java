package com.restapi.dto;

public class NextQuestionResponseDTO {

	private String correctAnswer;
	private QuestionDTO nextQuestion;

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public QuestionDTO getNextQuestion() {
		return nextQuestion;
	}

	public void setNextQuestion(QuestionDTO nextQuestion) {
		this.nextQuestion = nextQuestion;
	}
}
