package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.Answer;
import com.example.qa_backend.JSON.AnswerJSON;
import com.example.qa_backend.JSON.LoginResult;
import com.example.qa_backend.Service.AnswerService;
import com.example.qa_backend.Service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
                            @RequestBody String content) {
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
    public List<Answer> listAsked(@RequestParam int userId, @RequestParam int page_id){ return answerService.getAsked(page_id, userId); }
    @RequestMapping("/getLikedAnswer")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<Answer> getLiked(@RequestParam int page_id, @RequestParam int userId){ return answerService.getLiked(page_id, userId); }
    @RequestMapping("/getDislikedAnswer")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<Answer> getDisliked(@RequestParam int page_id, @RequestParam int userId){ return answerService.getDisliked(page_id, userId); }
    @RequestMapping("/deleteAnswer")
    @PreAuthorize("@authCheck.authorityCheck(1)")
    public LoginResult deleteAns(@RequestParam int aid) {
        answerService.deleteAnswer(aid);
        LoginResult result = new LoginResult();
        result.setCode(200);
        result.setToken("删除成功");
        return result;
    }
}
