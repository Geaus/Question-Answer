package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.Tag;
import com.example.qa_backend.JSON.QuestionJSON;
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
    public List<QuestionJSON> listQuestions(@RequestParam int uid) { return questionService.listQuestions(uid); }
    @RequestMapping("/findQuestion")
    public QuestionJSON findQuestion(@RequestParam int uid, @RequestParam int qid) { return questionService.findQuestion(uid, qid); }
    @RequestMapping("/askQuestion")
    public Question askQuestion(@RequestParam int uid, @RequestParam String content, @RequestParam String title,
                                @RequestBody List<Tag> tags) { return questionService.askQuestion(uid, content, title, tags); }
    @RequestMapping("/getAsked")
    public List<Question> listAsked(@RequestParam int uid) { return questionService.listAsked(uid); }
    @RequestMapping("/getLikedQuestion")
    public List<Question> getLiked(@RequestParam int uid) { return questionService.getLiked(uid); }
    @RequestMapping("/getDislikedQuestion")
    public List<Question> getDisliked(@RequestParam int uid) { return questionService.getDisliked(uid); }
    @RequestMapping("/getMarkedQuestion")
    public List<Question> getMarked(@RequestParam int uid) { return questionService.getMarked(uid); }
    @RequestMapping("/searchByTitle")
    public List<QuestionJSON> searchByTitle(@RequestParam String title, @RequestParam int uid) { return questionService.searchByTitle(title, uid); }
    @RequestMapping("/deleteQuestion")
    public void deleteQuestion(@RequestParam int qid) { questionService.deleteQuestion(qid); }
    @RequestMapping("/searchByTag")
    public List<QuestionJSON> searchByTag(@RequestParam int tag, @RequestParam int uid){return questionService.searchByTag(tag, uid);}
}
