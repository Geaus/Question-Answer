package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.Answer;
import com.example.qa_backend.JSON.AnswerJSON;
import com.example.qa_backend.Service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping("/getAnswer")
    public List<AnswerJSON> getAnswer(@RequestParam int qid) { return answerService.getAnswer(qid); }
    @RequestMapping("/addAnswer")
    public Answer addAnswer(@RequestParam int uid, @RequestParam int qid,
                            @RequestParam String content) { return answerService.addAnswer(uid, qid, content); }
    @RequestMapping("/getAnswered")
    public List<Answer> listAsked(@RequestParam int uid){ return answerService.getAsked(uid); }
    @RequestMapping("/getLikedAnswer")
    public List<Answer> getLiked(@RequestParam int uid){ return answerService.getLiked(uid); }
    @RequestMapping("/getDislikedAnswer")
    public List<Answer> getDisliked(@RequestParam int uid){ return answerService.getDisliked(uid); }
}
