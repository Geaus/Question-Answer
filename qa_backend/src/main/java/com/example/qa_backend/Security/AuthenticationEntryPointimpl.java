package com.example.qa_backend.Security;

import com.example.qa_backend.Entity.User;
import com.example.qa_backend.JSON.LoginResult;
import com.example.qa_backend.JSON.UserJSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthenticationEntryPointimpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        User user = new User();
        user.setId(-1);
        LoginResult loginResult = new LoginResult();
        loginResult.setCode(401);
        loginResult.setToken("用户认证失败，请重新登录");
        UserJSON json = new UserJSON();
        json.setUser(user);
        json.setResult(loginResult);
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(json.toString());
    }
}
