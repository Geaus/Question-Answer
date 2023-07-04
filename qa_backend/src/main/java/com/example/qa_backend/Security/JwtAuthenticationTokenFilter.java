package com.example.qa_backend.Security;

import com.example.qa_backend.Dao.UserDao;
import com.example.qa_backend.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    UserDao userDao;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        String raw = request.getParameter("uid");
        int id = 0;
        if(raw != null)id = Integer.parseInt(raw);
        //如果token为空直接放行，由于用户信息没有存放在SecurityContextHolder.getContext()中所以后面的过滤器依旧认证失败符合要求
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request, response);
            return;
        }
        int userId;
        try {
            //通过jwt工具类解析token获得userId,如果token过期或非法就会抛异常
            userId = JwtUtil.getUserId(token);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        User user = userDao.findUser(userId);
        if(id != 0 && userId != id)user = null;
        if(user == null) {
            throw new RuntimeException("用户未登录");
        }
        //将用户信息存放在SecurityContextHolder.getContext()，后面的过滤器就可以获得用户信息了。
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user,null,null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request,response);
    }
}
