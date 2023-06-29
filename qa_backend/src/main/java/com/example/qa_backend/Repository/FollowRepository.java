package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<Follow> findFollowsByUser1Id(int id);
    @Modifying
    @Transactional
    void deleteFollowByUser1IdAndUser2Id(int uid_1, int uid_2);
    Follow findFollowByUser1IdAndUser2Id(int uid_1, int uid_2);
}
