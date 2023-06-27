package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.Tag;
import com.example.qa_backend.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @RequestMapping("/getQuestions")
    public List<Question> listQuestions() { return questionService.listQuestions(); }
    @RequestMapping("/findQuestion")
    public Question findQuestion(@RequestParam int questionId) { return questionService.findQuestion(questionId); }
    @RequestMapping("/askQuestion")
    public Question askQuestion(@RequestParam int userId, @RequestParam String content, @RequestParam String title,
                                @RequestBody List<Tag> tags) { return questionService.askQuestion(userId, content, title, tags); }
    @RequestMapping("/getAsked")
    public List<Question> listAsked(@RequestParam int userId) { return questionService.listAsked(userId); }
}
