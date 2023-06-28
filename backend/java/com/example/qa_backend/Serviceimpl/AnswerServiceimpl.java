package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.AnswerDao;
import com.example.qa_backend.Dao.FeedbackAnswerDao;
import com.example.qa_backend.Dao.QuestionDao;
import com.example.qa_backend.Dao.UserDao;
import com.example.qa_backend.Entity.Answer;
import com.example.qa_backend.Entity.FeedbackForAnswer;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.JSON.AnswerJSON;
import com.example.qa_backend.Service.AnswerService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @Autowired
    FeedbackAnswerDao feedbackAnswerDao;
    @Override
    public List<AnswerJSON> getAnswer(int questionId) {
        Question question = questionDao.getQuestion(questionId);
        List<Answer> ans = answerDao.findAnswers(question);
        List<AnswerJSON> res = new ArrayList<>();
        for(int i = 0; i < ans.size(); i++) {
            Answer a = ans.get(i);
            List<FeedbackForAnswer> feedback = feedbackAnswerDao.findFeedback(a.getId());
            int like = 0, dislike = 0;
            for(int j = 0; j < feedback.size(); j++) {
                if(feedback.get(j).getLike() == 1) like++;
                else dislike++;
            }
            AnswerJSON tmp = new AnswerJSON();
            tmp.setId(a.getId());
            tmp.setContent(a.getContent());
            tmp.setLike(like);
            tmp.setQuestion(a.getQuestion());
            tmp.setUser(a.getUser());
            tmp.setCreateTime(a.getCreateTime());
            tmp.setDislike(dislike);
            res.add(tmp);
        }
        return res;
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

    @Override
    public List<Answer> getAsked(int userId) {
        return answerDao.findAnswered(userDao.findUser(userId));
    }

    @Override
    public List<Answer> getLiked(int userId) {
        List<FeedbackForAnswer> feedback = feedbackAnswerDao.listRelatedAns(userId);
        List<Answer> ans = new ArrayList<>();
        for(int i = 0; i < feedback.size(); i++) {
            if(feedback.get(i).getLike() != 1)continue;
            ans.add(answerDao.findAnswer(feedback.get(i).getAnsId()));
        }
        return ans;
    }

    @Override
    public List<Answer> getDisliked(int userId) {
        List<FeedbackForAnswer> feedback = feedbackAnswerDao.listRelatedAns(userId);
        List<Answer> ans = new ArrayList<>();
        for(int i = 0; i < feedback.size(); i++) {
            if(feedback.get(i).getLike() != -1)continue;
            ans.add(answerDao.findAnswer(feedback.get(i).getAnsId()));
        }
        return ans;
    }
}
