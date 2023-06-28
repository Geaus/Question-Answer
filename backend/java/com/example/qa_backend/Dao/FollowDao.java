package com.example.qa_backend.Dao;

import com.example.qa_backend.Entity.Follow;

import java.util.List;

public interface FollowDao {
    List<Follow> findFollowList(int id);
}
