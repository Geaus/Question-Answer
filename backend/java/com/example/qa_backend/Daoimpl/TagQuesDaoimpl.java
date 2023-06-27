package com.example.qa_backend.Daoimpl;

import com.example.qa_backend.Dao.TagQuesDao;
import com.example.qa_backend.Entity.TagQuesRelation;
import com.example.qa_backend.Repository.TagQuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TagQuesDaoimpl implements TagQuesDao {
    @Autowired
    TagQuesRepository tagQuesRepository;
    @Override
    public TagQuesRelation addRelation(TagQuesRelation tagQuesRelation) {
        return tagQuesRepository.save(tagQuesRelation);
    }
}
