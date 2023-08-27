package com.example.qa_backend.Controller;

import com.example.qa_backend.Service.WordCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class WordCloudController {
    @Autowired
    WordCloudService wordCloudService;
    @RequestMapping("/wordCloud")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public String getWordCloud() {
        return wordCloudService.get();
    }
}
