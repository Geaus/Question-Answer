package com.example.qa_backend.Service;

import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.Tag;
import com.example.qa_backend.JSON.QuestionJSON;

import java.util.List;

public interface QuestionService {
    List<QuestionJSON> listQuestions(int page_id, int uid);
    QuestionJSON findQuestion(int uid, int id);
    Question askQuestion(int userId, String content, String title, List<Tag> tags);
    List<Question> listAsked(int page_id, int userId);
    List<Question> getLiked(int page_id, int userId);
    List<Question> getDisliked(int page_id, int userId);
    List<Question> getMarked(int page_id, int userId);
    List<QuestionJSON> searchByTitle(String searchTerm, int uid);
    void deleteQuestion(int qid);
}
