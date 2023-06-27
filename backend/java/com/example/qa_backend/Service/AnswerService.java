package com.example.qa_backend.Service;

import com.example.qa_backend.Entity.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> getAnswer(int questionId);

    Answer addAnswer(int userId, int questionId, String content);
}
