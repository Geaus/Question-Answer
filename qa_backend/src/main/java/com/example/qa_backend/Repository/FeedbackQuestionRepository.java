package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.FeedbackForQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackQuestionRepository extends JpaRepository<FeedbackForQuestion, Integer> {
    List<FeedbackForQuestion> findFeedbackForQuestionsByUserId(int id);
    List<FeedbackForQuestion> findFeedbackForQuestionsByQuesId(int id);
    FeedbackForQuestion findFeedbackForQuestionByQuesIdAndUserId(int ques_id, int user_id);
}
