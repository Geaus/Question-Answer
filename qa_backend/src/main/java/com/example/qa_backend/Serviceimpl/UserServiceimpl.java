package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.FollowDao;
import com.example.qa_backend.Dao.UserDao;
import com.example.qa_backend.Data.LoginUser;
import com.example.qa_backend.Entity.Follow;
import com.example.qa_backend.Entity.User;
import com.example.qa_backend.JSON.LoginResult;
import com.example.qa_backend.JSON.UserJSON;
import com.example.qa_backend.Security.JwtUtil;
import com.example.qa_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    FollowDao followDao;
    @Autowired
    AuthenticationManager authenticationManager;
    @Override
    public List<User> getFollowList(int page_id, int uid) {
        List<Follow> follows = followDao.findFollowList(page_id, uid);
        List<User> users = new ArrayList<>();
        for(int i = 0; i < follows.size(); i++) {
            users.add(userDao.findUser(follows.get(i).getUser2Id()));
        }
        return users;
    }

    @Override
    public UserJSON loginCheck(String userName, String passWord) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, passWord);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserJSON userJSON = new UserJSON();
        if(authentication == null) {
            User user = new User();
            user.setId(-1);
            LoginResult loginResult = new LoginResult();
            loginResult.setCode(401);
            loginResult.setToken("认证失败");
            userJSON.setUser(user);
            userJSON.setResult(loginResult);
            return userJSON;
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int id = loginUser.getUser().getId();
        String token = JwtUtil.createToken(id);
        User user = loginUser.getUser();
        userJSON.setUser(user);
        LoginResult loginResult = new LoginResult();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean ok = true;
        try {
            if(formatter.parse(user.getExpire_time()).compareTo(date) > 0)ok = false;
        }
        catch (Exception e){
            throw new RuntimeException("时间解析错误");
        }
        if(user.getType() == -1) {
            User u = new User();
            u.setId(-1);
            userJSON.setUser(u);
            loginResult.setToken("用户已被封禁");
            loginResult.setCode(403);
        }
        else if(!ok) {
            User u = new User();
            u.setId(-1);
            userJSON.setUser(u);
            loginResult.setToken("用户已登录");
            loginResult.setCode(403);
        }
        else {
            Date exp = new Date(System.currentTimeMillis() + 1800000);
            String formattedDate = formatter.format(exp);
            user.setExpire_time(formattedDate);
            user.setToken(token);
            userDao.addOne(user);
            loginResult.setToken(token);
            loginResult.setCode(200);
        }
        userJSON.setResult(loginResult);
        return userJSON;
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
        if(user != null){
            res.setId(-1);
            res.setUserName("用户名已被占用");
        }
        else {
            res.setUserName(userName);
            res.setPassWord(passWord);
            res.setEmail(email);
            res = userDao.addOne(res);
        }
        return res;
    }

    @Override
    public void logout(int uid) {
        User user = userDao.findUser(uid);
        System.out.println(user.getExpire_time());
        user.setExpire_time("1999-01-01 00:00:00");
        user.setToken("");
        userDao.addOne(user);
    }
}
