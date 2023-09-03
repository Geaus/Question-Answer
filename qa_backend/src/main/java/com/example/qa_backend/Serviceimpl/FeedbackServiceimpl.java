package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.*;
import com.example.qa_backend.Entity.*;
import com.example.qa_backend.JSON.AnswerJSON;
import com.example.qa_backend.JSON.QuestionJSON;
import com.example.qa_backend.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceimpl implements FeedbackService {
    @Autowired
    FeedbackQuestionDao feedbackQuestionDao;
    @Autowired
    FeedbackAnswerDao feedbackAnswerDao;
    @Autowired
    FollowDao followDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    AnswerDao answerDao;
    @Autowired
    UserDao userDao;
    @Override
    public QuestionJSON changeQuestionFeedback(int uid, int qid, int value) {
        int likeFlag = 0, markFlag = 0;
        FeedbackForQuestion feedback = feedbackQuestionDao.findSpecific(qid, uid);
        Question question = questionDao.getQuestion(qid);
        User user = userDao.findUser(uid);

        if(feedback == null){
            feedback = new FeedbackForQuestion();
            feedback.setLike(0);
            feedback.setBookmark(0);
        }
        else {
            if(feedback.getLike() == 1 && ((value != 2) && (value != -2))){
                question.setLike(question.getLike() - 1);
                user.setLiked(user.getLiked() - 1);
            }
            if(feedback.getLike() == -1 && ((value != 2) && (value != -2))){
                question.setDislike(question.getDislike() - 1);
                user.setDisliked(user.getDisliked() - 1);
            }
            if(feedback.getBookmark() == 1 && ((value == 2) || (value == -2)))question.setMark(question.getMark() - 1);
        }
        feedback.setQuesId(qid);
        feedback.setUserId(uid);

        if(value == 1) {
            feedback.setLike(1);
            question.setLike(question.getLike() + 1);
            user.setLiked(user.getLiked() + 1);
            likeFlag = 1;
        }
        if(value == 0) feedback.setLike(0);
        if(value == -1) {
            feedback.setLike(-1);
            question.setDislike(question.getDislike() + 1);
            user.setDisliked(user.getDisliked() + 1);
            likeFlag = -1;
        }
        if(value == 2) {
            feedback.setBookmark(1);
            question.setMark(question.getMark() + 1);
            markFlag = 1;
        }
        if(value == -2) feedback.setBookmark(0);
        if(feedback.getBookmark() == 1)markFlag = 1;
        likeFlag = feedback.getLike();

        feedback = feedbackQuestionDao.addOne(feedback);
        question = questionDao.addQuestion(question);
        user = userDao.addOne(user);
        if(feedback.getBookmark() == 0 && feedback.getLike() == 0)feedbackQuestionDao.deleteSpecific(qid, uid);

        QuestionJSON res = new QuestionJSON();
        res.setId(question.getId());
        res.setContent(question.getContent());
        res.setCreateTime(question.getCreateTime());
        res.setTags(question.getTags());
        res.setTitle(question.getTitle());
        res.setUser(question.getUser());
        res.setLike(question.getLike());
        res.setDislike(question.getDislike());
        res.setMark(question.getMark());
        res.setLikeFlag(likeFlag);
        res.setMarkFlag(markFlag);
        return res;
    }

    @Override
    public AnswerJSON changeAnswerFeedback(int uid, int aid, int value) {
        FeedbackForAnswer feedback = feedbackAnswerDao.findSpecific(aid, uid);
        Answer a = answerDao.findAnswer(aid);
        User user = userDao.findUser(uid);
        int flag = 0;
        if(feedback == null)feedback = new FeedbackForAnswer();
        else {
            if(feedback.getLike() == 1){
                a.setLike(a.getLike() - 1);
                user.setLiked(user.getLiked() - 1);
            }
            else if(feedback.getLike() == -1){
                a.setDislike(a.getDislike() - 1);
                user.setDisliked(user.getDisliked() - 1);
            }
        }
        feedback.setAnsId(aid);
        feedback.setUserId(uid);
        if(value == 1) {
            feedback.setLike(1);
            a.setLike(a.getLike() + 1);
            user.setLiked(user.getLiked() + 1);
            flag = 1;
        }
        if(value == 0) feedback.setLike(0);
        if(value == -1) {
            feedback.setLike(-1);
            a.setDislike(a.getDislike() + 1);
            user.setDisliked(user.getDisliked() + 1);
            flag = -1;
        }
        feedback = feedbackAnswerDao.addOne(feedback);
        a = answerDao.addAnswer(a);
        user = userDao.addOne(user);
        if(feedback.getLike() == 0)feedbackAnswerDao.deleteSpecific(aid, uid);
        Follow follow = followDao.check(uid, a.getUser().getId());
        AnswerJSON tmp = new AnswerJSON();
        if(follow != null)tmp.setFollowFlag(1);
        else tmp.setFollowFlag(0);
        tmp.setId(a.getId());
        tmp.setContent(a.getContent());
        tmp.setLike(a.getLike());
        tmp.setQuestion(a.getQuestion());
        tmp.setUser(a.getUser());
        tmp.setCreateTime(a.getCreateTime());
        tmp.setDislike(a.getDislike());
        tmp.setLikeFlag(flag);
        return tmp;
    }

    @Override
    public int changeFollow(int uid_1, int uid_2, int value) {
        if(value == 1) {
            Follow follow = new Follow();
            follow.setUser1Id(uid_1);
            follow.setUser2Id(uid_2);
            followDao.addOne(follow);
        }
        else {
            followDao.deleteOne(uid_1, uid_2);
        }

        return value;
    }


}
