package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.FeedbackForAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface FeedbackAnswerRepository extends JpaRepository<FeedbackForAnswer, Integer> {
    Page<FeedbackForAnswer> findFeedbackForAnswersByUserIdAndLike(Pageable pageable, int id, int like);
    List<FeedbackForAnswer> findFeedbackForAnswersByAnsId(int id);
    FeedbackForAnswer findFeedbackForAnswerByAnsIdAndUserId(int ans_id, int user_id);
    @Modifying
    @Transactional
    void deleteFeedbackForAnswerByAnsIdAndUserId(int ans_id, int user_id);
    @Modifying
    @Transactional
    void deleteFeedbackForAnswersByAnsId(int ans_id);
}
