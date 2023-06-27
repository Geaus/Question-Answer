package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
}
