package com.example.qa_backend.Dao;

import com.example.qa_backend.Entity.Answer;
import com.example.qa_backend.Entity.Question;

import java.util.List;

public interface AnswerDao {
    Answer addAnswer(Answer ans);

    List<Answer> findAnswers(Question question);
}
