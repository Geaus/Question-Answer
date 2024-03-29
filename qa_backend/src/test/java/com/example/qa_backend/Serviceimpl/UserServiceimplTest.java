package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.FollowDao;
import com.example.qa_backend.Dao.UserDao;
import com.example.qa_backend.Entity.Follow;
import com.example.qa_backend.Entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Component
class UserServiceimplTest {
    private UserServiceimpl userServiceimpl;
    @Mock
    private UserDao userDao;
    @Mock
    private FollowDao followDao;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userServiceimpl = new UserServiceimpl();
        userServiceimpl.userDao = userDao;
        userServiceimpl.followDao = followDao;
    }

    @AfterEach
    void tearDown() {
        this.userServiceimpl = null;
    }

    @Test
    void getFollowList() {
        List<Follow> expectedFollowList = new ArrayList<>();
        Follow follow1 = new Follow();
        follow1.setId(1);
        follow1.setUser1Id(1);
        follow1.setUser2Id(2);
        expectedFollowList.add(follow1);
        Follow follow2 = new Follow();
        follow2.setId(2);
        follow2.setUser1Id(1);
        follow2.setUser2Id(3);
        expectedFollowList.add(follow2);
        when(followDao.findFollowList(1)).thenReturn(expectedFollowList);

        User expectedUser1 = new User();
        expectedUser1.setId(1);
        User expectedUser2 = new User();
        expectedUser2.setId(2);
        User expectedUser3 = new User();
        expectedUser3.setId(3);
        when(userDao.findUser(1)).thenReturn(expectedUser1);
        when(userDao.findUser(2)).thenReturn(expectedUser2);
        when(userDao.findUser(3)).thenReturn(expectedUser3);

        List<User> followList = userServiceimpl.getFollowList(1);

        assertEquals(followList.size(), expectedFollowList.size());

        for (int i = 0; i < followList.size(); i++) {
            assertEquals(followList.get(i).getId(), expectedFollowList.get(i).getUser2Id());
        }
    }

    @Test
    void loginCheck() {
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setUserName("user1");
        expectedUser.setPassWord("1");
        User expectedBadUser = null;
        when(userDao.loginCheck("user1", "1")).thenReturn(expectedUser);
        when(userDao.loginCheck("user1", "2")).thenReturn(expectedBadUser);

        User user = userServiceimpl.loginCheck("user1", "1");
        User badUser = userServiceimpl.loginCheck("user1", "2");

        assertEquals(user.getId(), 1);
        assertEquals(badUser.getId(), -1);

    }

    @Test
    void findUser() {
        User expectedUser = new User();
        expectedUser.setId(1);
        when(userDao.findUser(1)).thenReturn(expectedUser);

        User user = userServiceimpl.findUser(1);

        assertEquals(user.getId(), expectedUser.getId());
    }

    @Test
    void changeType() {
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setType(1);
        User expectedUserTypeChanged = new User();
        expectedUserTypeChanged.setId(1);
        expectedUserTypeChanged.setType(2);
        when(userDao.findUser(1)).thenReturn(expectedUser);
        when(userDao.addOne(expectedUser)).thenReturn(expectedUserTypeChanged);

        User user = userServiceimpl.changeType(1, 2);

        assertEquals(user.getType(), 2);
    }

    @Test
    void register() {
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setUserName("user1");
        expectedUser.setPassWord("1");
        expectedUser.setEmail("user1@test.com");
        User expectedUserFailed = new User();

        when(userDao.nameCheck("user1")).thenReturn(null);
        when(userDao.nameCheck("user2")).thenReturn(expectedUserFailed);
        when(userDao.addOne(any(User.class))).thenReturn(expectedUser);

        User user = userServiceimpl.register("user1", "1", "user1@test.com");
        User userFailed = userServiceimpl.register("user2", "2", "user2@test.com");

        assertEquals(user.getId(), 1);
        assertEquals(userFailed.getId(), -1);
    }
}