package com.example.qa_backend.Daoimpl;

import com.example.qa_backend.Dao.FeedbackQuestionDao;
import com.example.qa_backend.Entity.FeedbackForQuestion;
import com.example.qa_backend.Repository.FeedbackQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class FeedbackQuestionDaoimpl implements FeedbackQuestionDao {
    @Autowired
    FeedbackQuestionRepository feedbackQuestionRepository;

    @Override
    public List<FeedbackForQuestion> findFeedback(int ques_id) {
        return feedbackQuestionRepository.findFeedbackForQuestionsByQuesId(ques_id);
    }

    @Override
    public List<FeedbackForQuestion> listRelatedQuestion(int user_id) {
        return feedbackQuestionRepository.findFeedbackForQuestionsByUserId(user_id);
    }

    @Override
    public FeedbackForQuestion findSpecific(int ques_id, int user_id) {
        return feedbackQuestionRepository.findFeedbackForQuestionByQuesIdAndUserId(ques_id, user_id);
    }

    @Override
    public FeedbackForQuestion addOne(FeedbackForQuestion feedback) {
        return feedbackQuestionRepository.save(feedback);
    }

    @Override
    public void deleteSpecific(int ques_id, int user_id) {
        feedbackQuestionRepository.deleteFeedbackForQuestionByQuesIdAndUserId(ques_id, user_id);
    }

    @Override
    public void deleteByQues(int ques_id) {
        feedbackQuestionRepository.deleteFeedbackForQuestionsByQuesId(ques_id);
    }
}
