package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<KeywordEntity, Integer> {
    List<KeywordEntity> findAllByKeywordContains(String keyword);
}