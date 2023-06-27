package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.Answer;
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
    public List<Answer> getAnswer(@RequestParam int questionId) { return answerService.getAnswer(questionId); }
    @RequestMapping("/addAnswer")
    public Answer addAnswer(@RequestParam int userId, @RequestParam int questionId,
                            @RequestParam String content) { return answerService.addAnswer(userId, questionId, content); }
}
