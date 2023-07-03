package com.example.qa_backend.Dao;

import com.example.qa_backend.Entity.KeywordEntity;

import java.util.List;

public interface KeywordDao {
    List<KeywordEntity> findKeyword(String keyword);
    KeywordEntity addOne(KeywordEntity keyword);
}
