package com.example.qa_backend.Daoimpl;

import com.example.qa_backend.Dao.UserDao;
import com.example.qa_backend.Entity.User;
import com.example.qa_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoimpl implements UserDao {
    @Autowired
    UserRepository userRepository;
    @Override
    public User findUser(int id) {
        return userRepository.findUserById(id);
    }
}
