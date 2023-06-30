package com.example.qa_backend.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "keyword", schema = "qa", catalog = "")
public class KeywordEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "keyword")
    private String keyword;
    @Basic
    @Column(name = "question_id")
    private Integer questionId;

    public Object getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeywordEntity that = (KeywordEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(keyword, that.keyword) && Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyword, questionId);
    }
}
