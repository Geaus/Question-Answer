package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findAll();
    Question findQuestionById(int id);
    List<Question> findQuestionsByUser(User user);
    List<Question> findQuestionsByTitleLikeOrContentLike(String title, String content);
}
