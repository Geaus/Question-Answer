package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.User;
import com.example.qa_backend.JSON.UserJSON;
import com.example.qa_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/getFollowed")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<User> getFollowed(@RequestParam int uid) { return userService.getFollowList(uid); }
    @RequestMapping("/login")
    public UserJSON loginCheck(@RequestParam String userName, @RequestParam String passWord) { return userService.loginCheck(userName, passWord); }
    @RequestMapping("/getUser")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public User findUser(@RequestParam int uid) { return userService.findUser(uid); }
    @RequestMapping("/banUser")
    @PreAuthorize("@authCheck.authorityCheck(1)")
    public User banUser(@RequestParam int uid) { return userService.changeType(uid, -1); }
    @RequestMapping("/register")
    public User register(@RequestParam String userName, @RequestParam String passWord,
                         @RequestParam String email){ return userService.register(userName, passWord, email); }
    @RequestMapping("/logout")
    public void logout(@RequestParam int uid) { userService.logout(uid); }
}
