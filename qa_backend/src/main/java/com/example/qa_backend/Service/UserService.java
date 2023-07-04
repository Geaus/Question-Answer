package com.example.qa_backend.Service;

import com.example.qa_backend.Entity.User;
import com.example.qa_backend.JSON.UserJSON;

import java.util.List;

public interface UserService {
    List<User> getFollowList(int uid);
    UserJSON loginCheck(String userName, String passWord);
    User findUser(int uid);
    User changeType(int uid, int type);
    User register(String userName, String passWord, String email);
    void logout(int uid);
}
