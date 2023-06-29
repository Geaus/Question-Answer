package com.example.qa_backend.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "feedback_a", schema = "qa", catalog = "")
public class FeedbackForAnswer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "`like`")
    private Integer like;
    @Basic
    @Column(name = "user_id")
    private Integer userId;
    @Basic
    @Column(name = "ans_id")
    private Integer ansId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAnsId() {
        return ansId;
    }

    public void setAnsId(Integer ansId) {
        this.ansId = ansId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackForAnswer that = (FeedbackForAnswer) o;
        return id == that.id && Objects.equals(like, that.like) && Objects.equals(userId, that.userId) && Objects.equals(ansId, that.ansId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, like, userId, ansId);
    }
}
