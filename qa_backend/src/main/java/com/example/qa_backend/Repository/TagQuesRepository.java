package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.TagQuesRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface TagQuesRepository extends JpaRepository<TagQuesRelation, Integer> {
    @Modifying
    @Transactional
    void deleteTagQuesRelationsByQuesId(int ques_id);
}
