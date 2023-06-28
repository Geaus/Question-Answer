package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.FeedbackAnswerDao;
import com.example.qa_backend.Dao.FeedbackQuestionDao;
import com.example.qa_backend.Dao.FollowDao;
import com.example.qa_backend.Entity.FeedbackForAnswer;
import com.example.qa_backend.Entity.FeedbackForQuestion;
import com.example.qa_backend.Entity.Follow;
import com.example.qa_backend.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceimpl implements FeedbackService {
    @Autowired
    FeedbackQuestionDao feedbackQuestionDao;
    @Autowired
    FeedbackAnswerDao feedbackAnswerDao;
    @Autowired
    FollowDao followDao;
    @Override
    public FeedbackForQuestion changeQuestionFeedback(int uid, int qid, int value) {
        FeedbackForQuestion feedback = feedbackQuestionDao.findSpecific(qid, uid);
        if(feedback == null)feedback = new FeedbackForQuestion();
        feedback.setQuesId(qid);
        feedback.setUserId(uid);
        if(value == 1) feedback.setLike(1);
        if(value == 0) feedback.setLike(0);
        if(value == -1) feedback.setLike(-1);
        if(value == 2) feedback.setBookmark(1);
        return feedbackQuestionDao.addOne(feedback);
    }

    @Override
    public FeedbackForAnswer changeAnswerFeedback(int uid, int aid, int value) {
        FeedbackForAnswer feedback = feedbackAnswerDao.findSpecific(aid, uid);
        if(feedback == null)feedback = new FeedbackForAnswer();
        feedback.setAnsId(aid);
        feedback.setUserId(uid);
        if(value == 1) feedback.setLike(1);
        if(value == 0) feedback.setLike(0);
        if(value == -1) feedback.setLike(-1);
        return feedbackAnswerDao.addOne(feedback);
    }

    @Override
    public void changeFollow(int uid_1, int uid_2, int value) {
        if(value == 1) {
            Follow follow = new Follow();
            follow.setUser1Id(uid_1);
            follow.setUser2Id(uid_2);
            followDao.addOne(follow);
        }
        else {
            followDao.deleteOne(uid_1, uid_2);
        }
    }
}
