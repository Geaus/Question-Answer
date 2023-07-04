package com.example.qa_backend.Security;

import com.example.qa_backend.Data.LoginUser;
import com.example.qa_backend.Entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("authCheck")
public class MyAuthentication {
    public boolean authorityCheck(int prio){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User myUser = (User) authentication.getPrincipal();
        return myUser.getType() >= prio;
    }
}
