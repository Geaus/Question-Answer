package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.SensitiveWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensitiveWordRepository extends JpaRepository<SensitiveWord, Integer> {
    List<SensitiveWord> findAll();
}
