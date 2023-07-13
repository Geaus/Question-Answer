package com.example.qa_backend.Daoimpl;

import com.example.qa_backend.Dao.FollowDao;
import com.example.qa_backend.Entity.Follow;
import com.example.qa_backend.Repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FollowDaoimpl implements FollowDao {
    @Autowired
    FollowRepository followRepository;

    @Override
    public List<Follow> findFollowList(int page_id, int id) {
        return followRepository.findFollowsByUser1Id(PageRequest.of(page_id, 10), id).toList();
    }

    @Override
    public void addOne(Follow follow) {
        followRepository.save(follow);
    }

    @Override
    public void deleteOne(int uid_1, int uid_2) {
        followRepository.deleteFollowByUser1IdAndUser2Id(uid_1, uid_2);
    }

    @Override
    public Follow check(int uid_1, int uid_2) {
        return followRepository.findFollowByUser1IdAndUser2Id(uid_1, uid_2);
    }
}
