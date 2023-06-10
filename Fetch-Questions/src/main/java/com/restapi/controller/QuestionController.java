package com.restapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restapi.dto.AnswerRequestDTO;
import com.restapi.dto.NextQuestionResponseDTO;
import com.restapi.dto.QuestionDTO;
import com.restapi.service.QuestionService;

@RestController
@RequestMapping("/api")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/play")
    public ResponseEntity<QuestionDTO> play() {
        try {
            QuestionDTO questionDTO = questionService.getQuestionById(null);
            return ResponseEntity.ok(questionDTO);
        } catch (QuestionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/next")
    public ResponseEntity<NextQuestionResponseDTO> next(@RequestBody AnswerRequestDTO answerRequest) {
        try {
            NextQuestionResponseDTO responseDTO = questionService.checkAnswerAndGetNextQuestion(answerRequest.getQuestionId(), answerRequest.getAnswer());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<String> handleQuestionNotFoundException(QuestionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
    }
}

