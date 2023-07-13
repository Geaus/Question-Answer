package com.example.qa_backend.Service;

import com.example.qa_backend.Entity.Answer;
import com.example.qa_backend.JSON.AnswerJSON;

import java.util.List;

public interface AnswerService {
    List<AnswerJSON> getAnswer(int page_id, int userId, int questionId);
    Answer addAnswer(int userId, int questionId, String content);
    List<Answer> getAsked(int page_id, int userId);
    List<Answer> getLiked(int page_id, int userId);
    List<Answer> getDisliked(int page_id, int userId);
    void deleteAnswer(int aid);
}
