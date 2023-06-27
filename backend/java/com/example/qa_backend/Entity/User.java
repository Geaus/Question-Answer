package com.example.qa_backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "type")
    private Integer type;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Question> questions;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Answer> answers;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "follow",
                joinColumns = {@JoinColumn(name = "user_1_id", referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "user_2_id", referencedColumnName = "id")})
    private List<User> followList;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "followList")
    @JsonIgnore
    private List<User> fanList;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "feedback_a",
                joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "ans_id", referencedColumnName = "id")})
    private List<Answer> relatedAnswer;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "feedback_q",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "ques_id", referencedColumnName = "id")})
    private List<Answer> relatedQuestion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<User> getFollowList() {
        return followList;
    }

    public void setFollowList(List<User> followList) {
        this.followList = followList;
    }

    public List<User> getFanList() {
        return fanList;
    }

    public void setFanList(List<User> fanList) {
        this.fanList = fanList;
    }

    public List<Answer> getRelatedAnswer() {
        return relatedAnswer;
    }

    public void setRelatedAnswer(List<Answer> relatedAnswer) {
        this.relatedAnswer = relatedAnswer;
    }

    public List<Answer> getRelatedQuestion() {
        return relatedQuestion;
    }

    public void setRelatedQuestion(List<Answer> relatedQuestion) {
        this.relatedQuestion = relatedQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(type, user.type) && Objects.equals(questions, user.questions) && Objects.equals(answers, user.answers) && Objects.equals(followList, user.followList) && Objects.equals(fanList, user.fanList) && Objects.equals(relatedAnswer, user.relatedAnswer) && Objects.equals(relatedQuestion, user.relatedQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, questions, answers, followList, fanList, relatedAnswer, relatedQuestion);
    }
}
