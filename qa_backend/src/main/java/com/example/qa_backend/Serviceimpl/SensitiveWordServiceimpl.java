package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.SensitiveWordDao;
import com.example.qa_backend.SensitiveWord.SimpleTrie;
import com.example.qa_backend.Service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class SensitiveWordServiceimpl implements SensitiveWordService {
    private SimpleTrie trie;
    @Autowired
    SensitiveWordDao sensitiveWordDao;
    @Override
    @PostConstruct
    public void init() {
        List<String> strs = sensitiveWordDao.findAllWord();
        trie = new SimpleTrie(strs);
    }

    @Override
    public boolean isTextValid(String text) {
        return trie.isValid(text);
    }
}
