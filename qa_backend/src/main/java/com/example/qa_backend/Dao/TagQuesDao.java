package com.example.qa_backend.Dao;

import com.example.qa_backend.Entity.TagQuesRelation;

import java.util.List;

public interface TagQuesDao {
    TagQuesRelation addRelation(TagQuesRelation tagQuesRelation);
    void deleteRelation(int ques_id);

    List<TagQuesRelation> searchByTagId(int tagId, int page_id);
}
