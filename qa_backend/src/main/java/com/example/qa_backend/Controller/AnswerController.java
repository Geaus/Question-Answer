package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.Answer;
import com.example.qa_backend.JSON.AnswerJSON;
import com.example.qa_backend.Service.AnswerService;
import com.example.qa_backend.Service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class AnswerController {
    @Autowired
    AnswerService answerService;
    @Autowired
    SensitiveWordService sensitiveWordService;
    @RequestMapping("/getAnswer")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<AnswerJSON> getAnswer(@RequestParam int uid, @RequestParam int qid,
                                      @RequestParam int page_id) { return answerService.getAnswer(page_id, uid, qid); }
    @RequestMapping("/addAnswer")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public Answer addAnswer(@RequestParam int uid, @RequestParam int qid,
                            @RequestParam String content) {
        if(!sensitiveWordService.isTextValid(content)) {
            Answer answer = new Answer();
            answer.setId(-1);
            answer.setContent("回答内容不合法");
            return answer;
        }
        return answerService.addAnswer(uid, qid, content);
    }
    @RequestMapping("/getAnswered")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<Answer> listAsked(@RequestParam int uid, @RequestParam int page_id){ return answerService.getAsked(page_id, uid); }
    @RequestMapping("/getLikedAnswer")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<Answer> getLiked(@RequestParam int page_id, @RequestParam int uid){ return answerService.getLiked(page_id, uid); }
    @RequestMapping("/getDislikedAnswer")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<Answer> getDisliked(@RequestParam int page_id, @RequestParam int uid){ return answerService.getDisliked(page_id, uid); }
    @RequestMapping("/deleteAnswer")
    @PreAuthorize("@authCheck.authorityCheck(1)")
    public void deleteAns(@RequestParam int aid){ answerService.deleteAnswer(aid); }
}
