package com.example.qa_backend.Service;

import com.example.qa_backend.Entity.User;

import java.util.List;

public interface UserService {
    List<User> getFollowList(int uid);
    User loginCheck(String userName, String passWord);
    User findUser(int uid);
}
