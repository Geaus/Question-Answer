package com.example.qa_backend.Daoimpl;

import com.example.qa_backend.Dao.FollowDao;
import com.example.qa_backend.Entity.Follow;
import com.example.qa_backend.Repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FollowDaoimpl implements FollowDao {
    @Autowired
    FollowRepository followRepository;

    @Override
    public List<Follow> findFollowList(int id) {
        return followRepository.findFollowsByUser1Id(id);
    }

    @Override
    public Follow addOne(Follow follow) {
        return followRepository.save(follow);
    }

    @Override
    public void deleteOne(int uid_1, int uid_2) {
        followRepository.deleteFollowByUser1IdAndUser2Id(uid_1, uid_2);
    }
}
