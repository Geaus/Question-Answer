package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.FeedbackForQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface FeedbackQuestionRepository extends JpaRepository<FeedbackForQuestion, Integer> {
    Page<FeedbackForQuestion> findFeedbackForQuestionsByUserIdAndLike(Pageable pageable, int id, int like);
    Page<FeedbackForQuestion> findFeedbackForQuestionsByUserIdAndBookmark(Pageable pageable, int id, int bookmark);
    List<FeedbackForQuestion> findFeedbackForQuestionsByQuesId(int id);
    FeedbackForQuestion findFeedbackForQuestionByQuesIdAndUserId(int ques_id, int user_id);
    @Modifying
    @Transactional
    void deleteFeedbackForQuestionByQuesIdAndUserId(int ques_id, int user_id);
    @Modifying
    @Transactional
    void deleteFeedbackForQuestionsByQuesId(int ques_id);
}
