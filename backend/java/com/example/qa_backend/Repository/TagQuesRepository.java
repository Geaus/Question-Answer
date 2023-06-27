package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.TagQuesRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagQuesRepository extends JpaRepository<TagQuesRelation, Integer> {
}
