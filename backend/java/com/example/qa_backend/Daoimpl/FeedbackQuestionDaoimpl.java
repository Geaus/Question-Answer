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
    public List<FeedbackForQuestion> listRelatedQuestion(int userId) {
        return feedbackQuestionRepository.findFeedbackForQuestionsByUserId(userId);
    }
}
