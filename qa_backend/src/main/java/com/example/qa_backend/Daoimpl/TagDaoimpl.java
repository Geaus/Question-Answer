package com.example.qa_backend.Daoimpl;

import com.example.qa_backend.Dao.TagDao;
import com.example.qa_backend.Entity.Tag;
import com.example.qa_backend.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class TagDaoimpl implements TagDao {
    @Autowired
    TagRepository tagRepository;
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }
}
