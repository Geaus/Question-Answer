package com.example.qa_backend.Dao;

import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.User;

import java.util.List;

public interface QuestionDao {
    List<Question> listQuestions();
    Question getQuestion(int id);
    Question addQuestion(Question question);
    List<Question> getAsked(User user);
    void deleteQuestion(Question question);

    List<Question> findQuestionsByTitleOrContentContaining(String keyword);
}
