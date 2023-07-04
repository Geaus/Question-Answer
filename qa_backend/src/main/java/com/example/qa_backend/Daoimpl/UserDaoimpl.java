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

    @Override
    public User loginCheck(String userName, String passWord) {
        return userRepository.findUserByUserNameAndPassWord(userName, passWord);
    }

    @Override
    public User addOne(User user) {
        return userRepository.save(user);
    }

    @Override
    public User nameCheck(String userName) {
        return userRepository.findUserByUserName(userName);
    }
}
