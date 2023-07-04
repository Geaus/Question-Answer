package com.example.qa_backend.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Follow {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "user_1_id")
    private Integer user1Id;
    @Basic
    @Column(name = "user_2_id")
    private Integer user2Id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(Integer user1Id) {
        this.user1Id = user1Id;
    }

    public Integer getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(Integer user2Id) {
        this.user2Id = user2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return id == follow.id && Objects.equals(user1Id, follow.user1Id) && Objects.equals(user2Id, follow.user2Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user1Id, user2Id);
    }
}
