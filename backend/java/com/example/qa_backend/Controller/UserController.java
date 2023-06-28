package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.User;
import com.example.qa_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/getFollowed")
    public List<User> getFollowed(@RequestParam int uid) { return userService.getFollowList(uid); }
    @RequestMapping("/login")
    public User loginCheck(@RequestParam String userName, @RequestParam String passWord) { return userService.loginCheck(userName, passWord); }
    @RequestMapping("/getUser")
    public User findUser(@RequestParam int uid) { return userService.findUser(uid); }
}
