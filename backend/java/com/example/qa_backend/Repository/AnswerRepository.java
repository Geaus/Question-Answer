package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.Answer;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findAnswersByQuestion(Question question);
}
