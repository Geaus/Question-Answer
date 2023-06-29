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
    @Override
    public QuestionJSON changeQuestionFeedback(int uid, int qid, int value) {
        FeedbackForQuestion feedback = feedbackQuestionDao.findSpecific(qid, uid);
        if(feedback == null){
            feedback = new FeedbackForQuestion();
            feedback.setLike(0);
            feedback.setBookmark(0);
        }
        feedback.setQuesId(qid);
        feedback.setUserId(uid);
        if(value == 1) feedback.setLike(1);
        if(value == 0) feedback.setLike(0);
        if(value == -1) feedback.setLike(-1);
        if(value == 2) feedback.setBookmark(1);
        feedback = feedbackQuestionDao.addOne(feedback);
        if(feedback.getBookmark() == 0 && feedback.getLike() == 0)feedbackQuestionDao.deleteSpecific(qid, uid);
        QuestionJSON res = new QuestionJSON();
        Question question = questionDao.getQuestion(qid);
        res.setId(question.getId());
        res.setContent(question.getContent());
        res.setCreateTime(question.getCreateTime());
        res.setTags(question.getTags());
        res.setTitle(question.getTitle());
        res.setUser(question.getUser());
        List<FeedbackForQuestion> feedbacks = feedbackQuestionDao.findFeedback(qid);
        int like = 0, dislike = 0, mark = 0, likeFlag = 0, markFlag = 0;
        for(int i = 0; i < feedbacks.size(); i++) {
            if(feedbacks.get(i).getLike() == -1){
                if(feedbacks.get(i).getUserId() == uid)likeFlag = -1;
                dislike++;
            }
            else if(feedbacks.get(i).getLike() == 1){
                if(feedbacks.get(i).getUserId() == uid)likeFlag = 1;
                like++;
            }
            if(feedbacks.get(i).getBookmark() == 1){
                if(feedbacks.get(i).getUserId() == uid)markFlag = 1;
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
    public AnswerJSON changeAnswerFeedback(int uid, int aid, int value) {
        FeedbackForAnswer feedback = feedbackAnswerDao.findSpecific(aid, uid);
        if(feedback == null)feedback = new FeedbackForAnswer();
        feedback.setAnsId(aid);
        feedback.setUserId(uid);
        if(value == 1) feedback.setLike(1);
        if(value == 0) feedback.setLike(0);
        if(value == -1) feedback.setLike(-1);
        feedback = feedbackAnswerDao.addOne(feedback);
        if(feedback.getLike() == 0)feedbackAnswerDao.deleteSpecific(aid, uid);
        Answer a = answerDao.findAnswer(aid);
        List<FeedbackForAnswer> feedbacks = feedbackAnswerDao.findFeedback(a.getId());
        int like = 0, dislike = 0, flag = 0;
        for(int j = 0; j < feedbacks.size(); j++) {
            if(feedbacks.get(j).getLike() == 1) {
                if(feedbacks.get(j).getUserId() == uid)flag = 1;
                like++;
            }
            else {
                if(feedbacks.get(j).getUserId() == uid)flag = -1;
                dislike++;
            }
        }
        Follow follow = followDao.check(uid, a.getUser().getId());
        AnswerJSON tmp = new AnswerJSON();
        if(follow != null)tmp.setFollowFlag(1);
        else tmp.setFollowFlag(0);
        tmp.setId(a.getId());
        tmp.setContent(a.getContent());
        tmp.setLike(like);
        tmp.setQuestion(a.getQuestion());
        tmp.setUser(a.getUser());
        tmp.setCreateTime(a.getCreateTime());
        tmp.setDislike(dislike);
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
