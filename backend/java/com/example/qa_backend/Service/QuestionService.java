package com.example.qa_backend.Service;

import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.Tag;

import java.util.List;

public interface QuestionService {
    List<Question> listQuestions();
    Question findQuestion(int id);
    Question askQuestion(int userId, String content, String title, List<Tag> tags);
    List<Question> listAsked(int userId);
}
