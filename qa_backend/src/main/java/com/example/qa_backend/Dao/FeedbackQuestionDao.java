package com.example.qa_backend.Dao;

import com.example.qa_backend.Entity.FeedbackForQuestion;

import java.util.List;

public interface FeedbackQuestionDao {
    List<FeedbackForQuestion> findFeedback(int ques_id);
    List<FeedbackForQuestion> listRelatedQuestion(int user_id);
    FeedbackForQuestion findSpecific(int ques_id, int user_id);
    FeedbackForQuestion addOne(FeedbackForQuestion feedback);
    void deleteSpecific(int ques_id, int user_id);
}
