package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.Tag;
import com.example.qa_backend.JSON.QuestionJSON;
import com.example.qa_backend.Service.QuestionService;
import com.example.qa_backend.Service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    SensitiveWordService sensitiveWordService;
    @RequestMapping("/getQuestions")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<QuestionJSON> listQuestions(@RequestParam int page_id, @RequestParam int uid) { return questionService.listQuestions(page_id, uid); }
    @RequestMapping("/findQuestion")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public QuestionJSON findQuestion(@RequestParam int uid, @RequestParam int qid) { return questionService.findQuestion(uid, qid); }
    @RequestMapping("/askQuestion")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public Question askQuestion(@RequestParam int uid, @RequestParam String content, @RequestParam String title,
                                @RequestBody List<Tag> tags)  throws IOException {
        if(!sensitiveWordService.isTextValid(content)) {
            Question question = new Question();
            question.setId(-1);
            question.setContent("提问内容不合法");
            return question;
        }
        return questionService.askQuestion(uid, content, title, tags);
    }
    @RequestMapping("/getAsked")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<Question> listAsked(@RequestParam int page_id, @RequestParam int userId) { return questionService.listAsked(page_id, userId); }
    @RequestMapping("/getLikedQuestion")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<Question> getLiked(@RequestParam int page_id, @RequestParam int userId) { return questionService.getLiked(page_id, userId); }
    @RequestMapping("/getDislikedQuestion")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<Question> getDisliked(@RequestParam int page_id, @RequestParam int userId) { return questionService.getDisliked(page_id, userId); }
    @RequestMapping("/getMarkedQuestion")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<Question> getMarked(@RequestParam int page_id, @RequestParam int userId) { return questionService.getMarked(page_id, userId); }
    @RequestMapping("/searchByTitle")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<QuestionJSON> searchByTitle(@RequestParam String title, @RequestParam int uid) { return questionService.searchByTitle(title, uid); }
    @RequestMapping("/deleteQuestion")
    @PreAuthorize("@authCheck.authorityCheck(1)")
    public void deleteQuestion(@RequestParam int qid) { questionService.deleteQuestion(qid); }
    @RequestMapping("/searchByTag")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<QuestionJSON> searchByTag(@RequestParam int tag, @RequestParam int uid){return questionService.searchByTag(tag, uid);}
    @RequestMapping("/fullTextSearch")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<QuestionJSON> fullTextSearch(@RequestParam String keyword, @RequestParam int uid) throws IOException {return questionService.fullTextSearch(keyword, uid);}
}
