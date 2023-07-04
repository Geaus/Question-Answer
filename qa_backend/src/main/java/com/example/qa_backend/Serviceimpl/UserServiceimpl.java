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

    @Override
    public User changeType(int uid, int type) {
        User user = userDao.findUser(uid);
        user.setType(type);
        return userDao.addOne(user);
    }

    @Override
    public User register(String userName, String passWord, String email) {
        User user = userDao.nameCheck(userName);
        User res = new User();
        if(user != null)res.setId(-1);
        else {
            res.setUserName(userName);
            res.setPassWord(passWord);
            res.setEmail(email);
            res = userDao.addOne(res);
        }
        return res;
    }
}
