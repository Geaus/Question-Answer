package com.example.qa_backend.Dao;

import com.example.qa_backend.Entity.FeedbackForAnswer;

import java.util.List;

public interface FeedbackAnswerDao {
    List<FeedbackForAnswer> findFeedback(int ans_id);
    List<FeedbackForAnswer> listRelatedAnsLike(int page_id, int user_id);
    List<FeedbackForAnswer> listRelatedAnsDislike(int page_id, int user_id);
    FeedbackForAnswer findSpecific(int ans_id, int user_id);
    FeedbackForAnswer addOne(FeedbackForAnswer feedback);
    void deleteSpecific(int ans_id, int user_id);
    void deleteByAns(int ans_id);
}
