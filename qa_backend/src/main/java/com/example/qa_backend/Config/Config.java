package com.example.qa_backend.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Config {
    @Value("$(lucene.indexLibrary)")
    public String indexLibrary;
}
