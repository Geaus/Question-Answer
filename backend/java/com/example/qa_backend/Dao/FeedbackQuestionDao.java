package com.example.qa_backend.Dao;

import com.example.qa_backend.Entity.FeedbackForQuestion;

import java.util.List;

public interface FeedbackQuestionDao {
    List<FeedbackForQuestion> listRelatedQuestion(int userId);
}
