package com.example.qa_backend.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tag_ques_rel", schema = "qa", catalog = "")
public class TagQuesRelation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "ques_id")
    private Integer quesId;
    @Basic
    @Column(name = "tag_id")
    private Integer tagId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getQuesId() {
        return quesId;
    }

    public void setQuesId(Integer quesId) {
        this.quesId = quesId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagQuesRelation that = (TagQuesRelation) o;
        return id == that.id && Objects.equals(quesId, that.quesId) && Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quesId, tagId);
    }
}
