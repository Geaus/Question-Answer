package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.QuestionDao;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestionServiceimpl implements QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Override
    public List<Question> listQuestions() {
        return questionDao.listQuestions();
    }

    @Override
    public Question findQuestion(int id) {
        return questionDao.getQuestion(id);
    }
}
