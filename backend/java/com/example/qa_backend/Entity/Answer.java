package com.example.qa_backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Answer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ques_id")
    private Question question;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "time")
    private String createTime;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "relatedAnswer")
    @JsonIgnore
    private List<User> relatedUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<User> getRelatedUser() {
        return relatedUser;
    }

    public void setRelatedUser(List<User> relatedUser) {
        this.relatedUser = relatedUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id == answer.id && Objects.equals(user, answer.user) && Objects.equals(question, answer.question) && Objects.equals(content, answer.content) && Objects.equals(createTime, answer.createTime) && Objects.equals(relatedUser, answer.relatedUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, question, content, createTime, relatedUser);
    }
}
