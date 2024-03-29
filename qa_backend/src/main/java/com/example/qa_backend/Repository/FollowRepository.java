package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    Page<Follow> findFollowsByUser1Id(Pageable pageable, int id);
    @Modifying
    @Transactional
    void deleteFollowByUser1IdAndUser2Id(int uid_1, int uid_2);
    Follow findFollowByUser1IdAndUser2Id(int uid_1, int uid_2);
}
