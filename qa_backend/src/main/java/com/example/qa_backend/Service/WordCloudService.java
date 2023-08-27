package com.example.qa_backend.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface WordCloudService {
    void generate() throws IOException;
    String get();
}
