package com.example.qa_backend.Service;

import com.example.qa_backend.JSON.QuestionJSON;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface WordCloudService {
    String get();
    List<QuestionJSON> getHotQuestion(int uid, int page_id);
}
