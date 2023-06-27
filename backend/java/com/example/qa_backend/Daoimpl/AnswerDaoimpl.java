package com.example.qa_backend.Daoimpl;

import com.example.qa_backend.Dao.AnswerDao;
import com.example.qa_backend.Entity.Answer;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerDaoimpl implements AnswerDao {
    @Autowired
    AnswerRepository answerRepository;

    @Override
    public Answer addAnswer(Answer ans) {
        return answerRepository.save(ans);
    }

    @Override
    public List<Answer> findAnswers(Question question) {
        return answerRepository.findAnswersByQuestion(question);
    }
}
