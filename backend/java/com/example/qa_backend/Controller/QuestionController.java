package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @RequestMapping("/getQuestions")
    public List<Question> listQuestions() { return questionService.listQuestions(); }
    @RequestMapping("/findQuestion")
    public Question findQuestion(int questionId) { return questionService.findQuestion(questionId); }
}
