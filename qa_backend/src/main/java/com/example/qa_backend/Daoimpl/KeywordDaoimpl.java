package com.example.qa_backend.Daoimpl;

import com.example.qa_backend.Dao.KeywordDao;
import com.example.qa_backend.Entity.KeywordEntity;
import com.example.qa_backend.Repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class KeywordDaoimpl implements KeywordDao {
    @Autowired
    KeywordRepository keywordRepository;
    @Override
    public List<KeywordEntity> findKeyword(String keyword) {
        return keywordRepository.findAllByKeywordContains(keyword);
    }

    @Override
    public KeywordEntity addOne(KeywordEntity keyword) {
        return keywordRepository.save(keyword);
    }

    @Override
    public void deleteKeyword(Integer ques_id){ keywordRepository.deleteKeywordEntitiesByQuestionId(ques_id); }

}
