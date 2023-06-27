package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.FeedbackForQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackQuestionRepository extends JpaRepository<FeedbackForQuestion, Integer> {
}
