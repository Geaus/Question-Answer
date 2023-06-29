package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.FeedbackForAnswer;
import com.example.qa_backend.Entity.FeedbackForQuestion;
import com.example.qa_backend.JSON.AnswerJSON;
import com.example.qa_backend.JSON.QuestionJSON;
import com.example.qa_backend.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;
    @RequestMapping("/feedbackQuestion")
    public QuestionJSON feedbackQuestion(@RequestParam int uid, @RequestParam int qid,
                                         @RequestParam int value) { return feedbackService.changeQuestionFeedback(uid, qid, value); }
    @RequestMapping("/feedbackAnswer")
    public AnswerJSON feedbackAnswer(@RequestParam int uid, @RequestParam int aid,
                                     @RequestParam int value) { return feedbackService.changeAnswerFeedback(uid, aid, value); }
    @RequestMapping("/changeFollow")
    public int changeFollow(@RequestParam int uid_1, @RequestParam int uid_2,
                             @RequestParam int value) {return feedbackService.changeFollow(uid_1, uid_2, value); }
}
