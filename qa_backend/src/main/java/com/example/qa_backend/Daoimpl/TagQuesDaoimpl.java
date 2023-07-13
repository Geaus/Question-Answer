package com.example.qa_backend.Daoimpl;

import com.example.qa_backend.Dao.TagQuesDao;
import com.example.qa_backend.Entity.TagQuesRelation;
import com.example.qa_backend.Repository.TagQuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagQuesDaoimpl implements TagQuesDao {
    @Autowired
    TagQuesRepository tagQuesRepository;
    @Override
    public TagQuesRelation addRelation(TagQuesRelation tagQuesRelation) {
        return tagQuesRepository.save(tagQuesRelation);
    }

    @Override
    public void deleteRelation(int ques_id) {
        tagQuesRepository.deleteTagQuesRelationsByQuesId(ques_id);
    }

    @Override
    public List<TagQuesRelation> searchByTagId(int tagId, int page_id){return tagQuesRepository.findAllByTagId(tagId, PageRequest.of(page_id, 10)).toList();}
}
