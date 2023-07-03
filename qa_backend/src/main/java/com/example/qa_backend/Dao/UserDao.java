package com.example.qa_backend.Dao;

import com.example.qa_backend.Entity.User;

public interface UserDao {
    User findUser(int id);
    User loginCheck(String userName, String passWord);
    User addOne(User user);
    User nameCheck(String userName);
}
