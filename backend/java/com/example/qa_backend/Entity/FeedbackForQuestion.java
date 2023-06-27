package com.example.qa_backend.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "feedback_q", schema = "qa", catalog = "")
public class FeedbackForQuestion {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "like")
    private Integer like;
    @Basic
    @Column(name = "bookmark")
    private Integer bookmark;
    @Basic
    @Column(name = "user_id")
    private Integer userId;
    @Basic
    @Column(name = "ques_id")
    private Integer quesId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getBookmark() {
        return bookmark;
    }

    public void setBookmark(Integer bookmark) {
        this.bookmark = bookmark;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuesId() {
        return quesId;
    }

    public void setQuesId(Integer quesId) {
        this.quesId = quesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackForQuestion that = (FeedbackForQuestion) o;
        return id == that.id && Objects.equals(like, that.like) && Objects.equals(bookmark, that.bookmark) && Objects.equals(userId, that.userId) && Objects.equals(quesId, that.quesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, like, bookmark, userId, quesId);
    }
}
