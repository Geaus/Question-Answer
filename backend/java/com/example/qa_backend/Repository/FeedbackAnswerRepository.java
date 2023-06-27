package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.FeedbackForAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackAnswerRepository extends JpaRepository<FeedbackForAnswer, Integer> {
}
