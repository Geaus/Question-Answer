package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.FeedbackForAnswer;
import com.example.qa_backend.Entity.FeedbackForQuestion;
import com.example.qa_backend.JSON.AnswerJSON;
import com.example.qa_backend.JSON.QuestionJSON;
import com.example.qa_backend.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public QuestionJSON feedbackQuestion(@RequestParam int uid, @RequestParam int qid,
                                         @RequestParam int value) { return feedbackService.changeQuestionFeedback(uid, qid, value); }
    @RequestMapping("/feedbackAnswer")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public AnswerJSON feedbackAnswer(@RequestParam int uid, @RequestParam int aid,
                                     @RequestParam int value) { return feedbackService.changeAnswerFeedback(uid, aid, value); }
    @RequestMapping("/changeFollow")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public int changeFollow(@RequestParam int uid, @RequestParam int uid_2,
                             @RequestParam int value) {return feedbackService.changeFollow(uid, uid_2, value); }
}
