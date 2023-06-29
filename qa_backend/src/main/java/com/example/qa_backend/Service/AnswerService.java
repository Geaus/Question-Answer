package com.example.qa_backend.Service;

import com.example.qa_backend.Entity.Answer;
import com.example.qa_backend.JSON.AnswerJSON;

import java.util.List;

public interface AnswerService {
    List<AnswerJSON> getAnswer(int userId, int questionId);
    Answer addAnswer(int userId, int questionId, String content);
    List<Answer> getAsked(int userId);
    List<Answer> getLiked(int userId);
    List<Answer> getDisliked(int userId);
}
