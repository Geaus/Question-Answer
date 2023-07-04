package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.UserDao;
import com.example.qa_backend.Data.LoginUser;
import com.example.qa_backend.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceimpl implements UserDetailsService {
    @Autowired
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.nameCheck(username);
        return new LoginUser(user);
    }
}
