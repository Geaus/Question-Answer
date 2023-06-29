package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.FollowDao;
import com.example.qa_backend.Dao.UserDao;
import com.example.qa_backend.Entity.Follow;
import com.example.qa_backend.Entity.User;
import com.example.qa_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceimpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    FollowDao followDao;
    @Override
    public List<User> getFollowList(int uid) {
        List<Follow> follows = followDao.findFollowList(uid);
        List<User> users = new ArrayList<>();
        for(int i = 0; i < follows.size(); i++) {
            users.add(userDao.findUser(follows.get(i).getUser2Id()));
        }
        return users;
    }

    @Override
    public User loginCheck(String userName, String passWord) {
        User user = userDao.loginCheck(userName, passWord);
        if(user == null) {
            user = new User();
            user.setId(-1);
        }
        return user;
    }

    @Override
    public User findUser(int uid) {
        return userDao.findUser(uid);
    }
}
