package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.AnswerDao;
import com.example.qa_backend.Dao.QuestionDao;
import com.example.qa_backend.Dao.UserDao;
import com.example.qa_backend.Entity.Answer;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Service.AnswerService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class AnswerServiceimpl implements AnswerService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    UserDao userDao;
    @Autowired
    AnswerDao answerDao;
    @Override
    public List<Answer> getAnswer(int questionId) {
        Question question = questionDao.getQuestion(questionId);
        return answerDao.findAnswers(question);
    }

    @Override
    public Answer addAnswer(int userId, int questionId, String content) {
        Answer ans = new Answer();
        ans.setContent(content);
        ans.setUser(userDao.findUser(userId));
        ans.setQuestion(questionDao.getQuestion(questionId));
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(date);
        ans.setCreateTime(formattedDate);
        return answerDao.addAnswer(ans);
    }
}
