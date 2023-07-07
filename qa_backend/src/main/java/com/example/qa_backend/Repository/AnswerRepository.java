package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.Answer;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    Page<Answer> findAnswersByQuestion(Pageable pageable, Question question);
    List<Answer> findAnswersByQuestion(Question question);
    Page<Answer> findAnswersByUser(Pageable pageable, User user);
    Answer findAnswerById(int id);
}
