package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.User;
import com.example.qa_backend.JSON.UserJSON;
import com.example.qa_backend.Service.SensitiveWordService;
import com.example.qa_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
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
    @Autowired
    SensitiveWordService sensitiveWordService;
    @RequestMapping("/getFollowed")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<User> getFollowed(@RequestParam int page_id, @RequestParam int userId) { return userService.getFollowList(page_id, userId); }
    @RequestMapping("/login")
    public UserJSON loginCheck(@RequestParam String userName, @RequestParam String passWord) { return userService.loginCheck(userName, passWord); }
    @RequestMapping("/getUser")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public User findUser(@RequestParam int userId) { return userService.findUser(userId); }
    @RequestMapping("/banUser")
    @PreAuthorize("@authCheck.authorityCheck(1)")
    public User banUser(@RequestParam int userId) { return userService.changeType(userId, -1); }
    @RequestMapping("/register")
    public User register(@RequestParam String userName, @RequestParam String passWord,
                         @RequestParam String email){
        if(!sensitiveWordService.isTextValid(userName)) {
            User user = new User();
            user.setId(-1);
            user.setUserName("用户名不合法");
            return user;
        }
        return userService.register(userName, passWord, email);
    }
    @RequestMapping("/logoutSystem")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public void logout(@RequestParam int uid) {
        System.out.println(1);
        userService.logout(uid);
    }
}
