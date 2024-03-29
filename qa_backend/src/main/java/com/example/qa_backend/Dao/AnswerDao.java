package com.example.qa_backend.Dao;

import com.example.qa_backend.Entity.Answer;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.User;

import java.util.List;

public interface AnswerDao {
    Answer addAnswer(Answer ans);
    List<Answer> findAnswersByPage(int page_id, Question question);
    List<Answer> findAnswers(Question question);
    List<Answer> findAnswered(int page_id, User user);
    Answer findAnswer(int id);
    void deleteAnswer(Answer answer);
}
