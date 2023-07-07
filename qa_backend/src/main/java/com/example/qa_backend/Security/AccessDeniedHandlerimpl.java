package com.example.qa_backend.Security;

import com.example.qa_backend.Entity.User;
import com.example.qa_backend.JSON.LoginResult;
import com.example.qa_backend.JSON.UserJSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AccessDeniedHandlerimpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        LoginResult loginResult = new LoginResult();
        loginResult.setCode(403);
        loginResult.setToken("用户权限错误");
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(loginResult.toString());
    }
}
