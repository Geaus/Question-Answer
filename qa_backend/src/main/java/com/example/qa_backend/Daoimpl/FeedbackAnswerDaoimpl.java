package com.example.qa_backend.Daoimpl;

import com.example.qa_backend.Dao.FeedbackAnswerDao;
import com.example.qa_backend.Entity.FeedbackForAnswer;
import com.example.qa_backend.Repository.FeedbackAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    public List<FeedbackForAnswer> listRelatedAnsLike(int page_id, int user_id) {
        return feedbackAnswerRepository.findFeedbackForAnswersByUserIdAndLike(PageRequest.of(page_id, 10), user_id, 1).toList();
    }
    @Override
    public List<FeedbackForAnswer> listRelatedAnsDislike(int page_id, int user_id) {
        return feedbackAnswerRepository.findFeedbackForAnswersByUserIdAndLike(PageRequest.of(page_id, 10), user_id, -1).toList();
    }

    @Override
    public FeedbackForAnswer findSpecific(int ans_id, int user_id) {
        return feedbackAnswerRepository.findFeedbackForAnswerByAnsIdAndUserId(ans_id, user_id);
    }

    @Override
    public FeedbackForAnswer addOne(FeedbackForAnswer feedback) {
        return feedbackAnswerRepository.save(feedback);
    }

    @Override
    public void deleteSpecific(int ans_id, int user_id) {
        feedbackAnswerRepository.deleteFeedbackForAnswerByAnsIdAndUserId(ans_id, user_id);
    }

    @Override
    public void deleteByAns(int ans_id) {
        feedbackAnswerRepository.deleteFeedbackForAnswersByAnsId(ans_id);
    }
}
