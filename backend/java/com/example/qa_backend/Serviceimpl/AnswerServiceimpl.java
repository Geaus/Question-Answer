package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.QuestionDao;
import com.example.qa_backend.Entity.Answer;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AnswerServiceimpl implements AnswerService {
    @Autowired
    QuestionDao questionDao;
    @Override
    public List<Answer> getAnswer(int questionId) {
        Question question = questionDao.getQuestion(questionId);
        return question.getAnswers();
    }
}
