package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<Follow> findFollowsByUser1Id(int id);
}
