package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface KeywordRepository extends JpaRepository<KeywordEntity, Integer> {
    List<KeywordEntity> findAllByKeywordContains(String keyword);

    @Modifying
    @Transactional
    void deleteKeywordEntitiesByQuestionId(Integer ques_id);
}