package com.example.qa_backend.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.sql.Date;

@Data
@Document(indexName = "question")
@Setting(shards = 6, replicas = 3)
public class Es {

    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;

    public Es() {
    }

    public void setId(long l) {
        this.id=l;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String s) {
        this.content = s;
    }

    // 省略getter/setter方法
}
