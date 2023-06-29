package com.example.qa_backend.Service;

import com.example.qa_backend.Entity.FeedbackForAnswer;
import com.example.qa_backend.Entity.FeedbackForQuestion;
import com.example.qa_backend.Entity.Follow;
import com.example.qa_backend.JSON.AnswerJSON;
import com.example.qa_backend.JSON.QuestionJSON;

public interface FeedbackService {
    QuestionJSON changeQuestionFeedback(int uid, int qid, int value);
    AnswerJSON changeAnswerFeedback(int uid, int aid, int value);
    int changeFollow(int uid_1, int uid_2, int value);
}
