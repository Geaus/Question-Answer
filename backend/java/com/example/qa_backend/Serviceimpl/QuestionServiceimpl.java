package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.FeedbackQuestionDao;
import com.example.qa_backend.Dao.QuestionDao;
import com.example.qa_backend.Dao.TagQuesDao;
import com.example.qa_backend.Dao.UserDao;
import com.example.qa_backend.Entity.FeedbackForQuestion;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.Tag;
import com.example.qa_backend.Entity.TagQuesRelation;
import com.example.qa_backend.JSON.QuestionJSON;
import com.example.qa_backend.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class QuestionServiceimpl implements QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    UserDao userDao;
    @Autowired
    TagQuesDao tagQuesDao;
    @Autowired
    FeedbackQuestionDao feedbackQuestionDao;
    @Override
    public List<QuestionJSON> listQuestions(int uid) {
        List<Question> ques = questionDao.listQuestions();
        List<QuestionJSON> resList = new ArrayList<>();
        for(int i = 0; i < ques.size(); i++) {
            Question question = ques.get(i);
            QuestionJSON res = new QuestionJSON();
            res.setId(question.getId());
            res.setContent(question.getContent());
            res.setCreateTime(question.getCreateTime());
            res.setTags(question.getTags());
            res.setTitle(question.getTitle());
            res.setUser(question.getUser());
            List<FeedbackForQuestion> feedback = feedbackQuestionDao.findFeedback(question.getId());
            int like = 0, dislike = 0, mark = 0, likeFlag = 0, markFlag = 0;
            for(int j = 0; j < feedback.size(); j++) {
                if(feedback.get(j).getLike() == -1){
                    if(feedback.get(j).getUserId() == uid)likeFlag = -1;
                    dislike++;
                }
                else if(feedback.get(j).getLike() == 1){
                    if(feedback.get(j).getUserId() == uid)likeFlag = 1;
                    like++;
                }
                if(feedback.get(j).getBookmark() == 1){
                    if(feedback.get(j).getUserId() == uid)markFlag = 1;
                    mark++;
                }
            }
            res.setLike(like);
            res.setDislike(dislike);
            res.setMark(mark);
            res.setLikeFlag(likeFlag);
            res.setMarkFlag(markFlag);
            resList.add(res);
        }
        return resList;
    }

    @Override
    public QuestionJSON findQuestion(int uid, int id) {
        Question question = questionDao.getQuestion(id);
        QuestionJSON res = new QuestionJSON();
        res.setId(question.getId());
        res.setContent(question.getContent());
        res.setCreateTime(question.getCreateTime());
        res.setTags(question.getTags());
        res.setTitle(question.getTitle());
        res.setUser(question.getUser());
        List<FeedbackForQuestion> feedback = feedbackQuestionDao.findFeedback(id);
        int like = 0, dislike = 0, mark = 0, likeFlag = 0, markFlag = 0;
        for(int i = 0; i < feedback.size(); i++) {
            if(feedback.get(i).getLike() == -1){
                if(feedback.get(i).getUserId() == uid)likeFlag = -1;
                dislike++;
            }
            else if(feedback.get(i).getLike() == 1){
                if(feedback.get(i).getUserId() == uid)likeFlag = 1;
                like++;
            }
            if(feedback.get(i).getBookmark() == 1){
                if(feedback.get(i).getUserId() == uid)markFlag = 1;
                mark++;
            }
        }
        res.setLike(like);
        res.setDislike(dislike);
        res.setMark(mark);
        res.setLikeFlag(likeFlag);
        res.setMarkFlag(markFlag);
        return res;
    }

    @Override
    public Question askQuestion(int userId, String content, String title, List<Tag> tags) {
        Question question = new Question();
        question.setContent(content);
        question.setUser(userDao.findUser(userId));
        question.setTitle(title);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(date);
        question.setCreateTime(formattedDate);
        question = questionDao.addQuestion(question);
        int ques_id = question.getId();
        for(int i = 0; i < tags.size(); i++) {
            TagQuesRelation tagQuesRelation = new TagQuesRelation();
            tagQuesRelation.setQuesId(ques_id);
            tagQuesRelation.setTagId(tags.get(i).getId());
            tagQuesDao.addRelation(tagQuesRelation);
        }
        return question;
    }

    @Override
    public List<Question> listAsked(int userId) {
        return questionDao.getAsked(userDao.findUser(userId));
    }

    @Override
    public List<Question> getLiked(int userId) {
        List<FeedbackForQuestion> feedback = feedbackQuestionDao.listRelatedQuestion(userId);
        List<Question> res = new ArrayList<>();
        for(int i = 0; i < feedback.size(); i++) {
            if(feedback.get(i).getLike() != 1)continue;
            res.add(questionDao.getQuestion(feedback.get(i).getQuesId()));
        }
        return res;
    }

    @Override
    public List<Question> getDisliked(int userId) {
        List<FeedbackForQuestion> feedback = feedbackQuestionDao.listRelatedQuestion(userId);
        List<Question> res = new ArrayList<>();
        for(int i = 0; i < feedback.size(); i++) {
            if(feedback.get(i).getLike() != -1)continue;
            res.add(questionDao.getQuestion(feedback.get(i).getQuesId()));
        }
        return res;
    }

    @Override
    public List<Question> getMarked(int userId) {
        List<FeedbackForQuestion> feedback = feedbackQuestionDao.listRelatedQuestion(userId);
        List<Question> res = new ArrayList<>();
        for(int i = 0; i < feedback.size(); i++) {
            if(feedback.get(i).getBookmark() != 1)continue;
            res.add(questionDao.getQuestion(feedback.get(i).getQuesId()));
        }
        return res;
    }
}
