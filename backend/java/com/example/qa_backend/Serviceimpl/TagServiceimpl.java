package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.TagDao;
import com.example.qa_backend.Entity.Tag;
import com.example.qa_backend.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceimpl implements TagService {
    @Autowired
    TagDao tagDao;

    @Override
    public List<Tag> listTags() {
        return tagDao.listTag();
    }
}
