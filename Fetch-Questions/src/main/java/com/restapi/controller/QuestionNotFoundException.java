package com.restapi.controller;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long questionId) {
        super("Question not found with ID: " + questionId);
    }
}