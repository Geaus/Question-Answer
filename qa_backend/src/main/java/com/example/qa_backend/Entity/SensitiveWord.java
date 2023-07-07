package com.example.qa_backend.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "t_sensitive_words", schema = "qa", catalog = "")
public class SensitiveWord {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "word")
    private String word;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensitiveWord that = (SensitiveWord) o;
        return Objects.equals(id, that.id) && Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word);
    }
}
