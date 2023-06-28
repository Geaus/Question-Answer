package com.example.qa_backend.Service;

import com.example.qa_backend.Entity.FeedbackForAnswer;
import com.example.qa_backend.Entity.FeedbackForQuestion;
import com.example.qa_backend.Entity.Follow;

public interface FeedbackService {
    FeedbackForQuestion changeQuestionFeedback(int uid, int qid, int value);
    FeedbackForAnswer changeAnswerFeedback(int uid, int aid, int value);
    void changeFollow(int uid_1, int uid_2, int value);
}
