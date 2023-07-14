package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Page<Question> findAll(Pageable pageable);
    Page<Question> findAllByTitleLike(String title, Pageable pageable);
    List<Question> findAllByTitleLike(String title);
    List<Question> findAll();
    Question findQuestionById(int id);
    Page<Question> findQuestionsByUser(Pageable pageable, User user);
}
