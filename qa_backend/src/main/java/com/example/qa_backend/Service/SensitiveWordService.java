package com.example.qa_backend.Service;

import java.util.List;

public interface SensitiveWordService {
    void init();
    boolean isTextValid(String text);
}
