package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
