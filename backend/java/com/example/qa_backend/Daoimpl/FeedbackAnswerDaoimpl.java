package com.example.qa_backend.Daoimpl;

import com.example.qa_backend.Dao.FeedbackAnswerDao;
import com.example.qa_backend.Entity.FeedbackForAnswer;
import com.example.qa_backend.Repository.FeedbackAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class FeedbackAnswerDaoimpl implements FeedbackAnswerDao {
    @Autowired
    FeedbackAnswerRepository feedbackAnswerRepository;
    @Override
    public List<FeedbackForAnswer> findFeedback(int ans_id) {
        return feedbackAnswerRepository.findFeedbackForAnswersByAnsId(ans_id);
    }

    @Override
    public List<FeedbackForAnswer> listRelatedAns(int user_id) {
        return feedbackAnswerRepository.findFeedbackForAnswersByUserId(user_id);
    }

    @Override
    public FeedbackForAnswer findSpecific(int ans_id, int user_id) {
        return feedbackAnswerRepository.findFeedbackForAnswerByAnsIdAndUserId(ans_id, user_id);
    }
}
