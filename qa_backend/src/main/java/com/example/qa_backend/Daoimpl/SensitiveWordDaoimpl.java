package com.example.qa_backend.Daoimpl;

import com.example.qa_backend.Dao.SensitiveWordDao;
import com.example.qa_backend.Entity.SensitiveWord;
import com.example.qa_backend.Repository.SensitiveWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class SensitiveWordDaoimpl implements SensitiveWordDao {
    @Autowired
    SensitiveWordRepository sensitiveWordRepository;
    @Override
    public List<String> findAllWord() {
        List<SensitiveWord> sensitiveWords = sensitiveWordRepository.findAll();
        List<String> str = new ArrayList<>();
        for (SensitiveWord sensitiveWord : sensitiveWords) str.add(sensitiveWord.getWord());
        return str;
    }
}
