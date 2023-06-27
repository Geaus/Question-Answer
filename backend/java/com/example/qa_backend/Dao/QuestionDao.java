package com.example.qa_backend.Dao;

import com.example.qa_backend.Entity.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> listQuestions();
    Question getQuestion(int id);
}
