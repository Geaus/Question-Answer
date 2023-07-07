package com.example.qa_backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "time")
    private String createTime;
    @Basic
    @Column(name = "likenum")
    private int like;
    @Basic
    @Column(name = "dislikenum")
    private int dislike;
    @Basic
    @Column(name = "marknum")
    private int mark;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "question")
    @JsonIgnore
    private List<Answer> answers;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "questions")
    private List<Tag> tags;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "relatedQuestion")
    @JsonIgnore
    private List<User> relatedUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id && Objects.equals(title, question.title) && Objects.equals(content, question.content) && Objects.equals(createTime, question.createTime) && Objects.equals(user, question.user) && Objects.equals(answers, question.answers) && Objects.equals(tags, question.tags) && Objects.equals(relatedUser, question.relatedUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, createTime, user, answers, tags, relatedUser);
    }
}
