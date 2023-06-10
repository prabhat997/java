package com.restapi.service;

import java.util.*;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.restapi.dto.NextQuestionResponseDTO;
import com.restapi.dto.QuestionDTO;
import com.restapi.entity.Question;
import com.restapi.repository.QuestionRepository;

@Service
public class QuestionService {
	
	@Autowired
    private final QuestionRepository questionRepository;
	
    private final RestTemplate restTemplate;

    public QuestionService(QuestionRepository questionRepository, RestTemplate restTemplate) {
        this.questionRepository = questionRepository;
        this.restTemplate = restTemplate;
    }

    public List<Question> fetchRandomQuestions(int count) {
        String apiUrl = "https://jservice.io/api/random?count=" + count;
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );

        List<Question> questions = new ArrayList<>();

        if (response.getStatusCode() == HttpStatus.OK) {
            List<Map<String, Object>> responseBody = response.getBody();
            if (responseBody != null) {
                for (Map<String, Object> questionMap : responseBody) {
                    long questionId = ((Number) questionMap.get("id")).longValue();
                    String questionText = (String) questionMap.get("question");
                    String answer = (String) questionMap.get("answer");

                    Question question = new Question();
                    question.setQuestionId(questionId);
                   
                    question.setAnswer(answer);

                    questions.add(question);
                }
            }
        }

        return questionRepository.saveAll(questions);
    }

    public QuestionDTO getQuestionById(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestionId(question.getQuestionId());
            questionDTO.setQuestionId(question.getQuestionId());
            return questionDTO;
        }
        return null;
    }

    public NextQuestionResponseDTO checkAnswerAndGetNextQuestion(Long questionId, String answer) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            String correctAnswer = question.getAnswer();
            NextQuestionResponseDTO responseDTO = new NextQuestionResponseDTO();
            responseDTO.setCorrectAnswer(correctAnswer);

            List<Question> allQuestions = questionRepository.findAll();
            int totalQuestions = allQuestions.size();
            int currentIndex = allQuestions.indexOf(question);
            int nextIndex = (currentIndex + 1) % totalQuestions;
            Question nextQuestion = allQuestions.get(nextIndex);

            QuestionDTO nextQuestionDTO = new QuestionDTO();
            nextQuestionDTO.setQuestionId(nextQuestion.getQuestionId());
            nextQuestionDTO.setQuestionId(nextQuestion.getQuestionId());

            responseDTO.setNextQuestion(nextQuestionDTO);

            return responseDTO;
        }
        return null;
    }
}