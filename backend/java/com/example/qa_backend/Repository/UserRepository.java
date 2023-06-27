package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(int id);
}
