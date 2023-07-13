package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.TagQuesRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface TagQuesRepository extends JpaRepository<TagQuesRelation, Integer> {
    @Modifying
    @Transactional
    void deleteTagQuesRelationsByQuesId(int ques_id);

    Page<TagQuesRelation> findAllByTagId(int tagId, Pageable pageable);
}
