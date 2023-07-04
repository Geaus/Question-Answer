package com.example.qa_backend.JSON;

import com.example.qa_backend.Entity.User;

public class UserJSON {
    private User user;
    private LoginResult result;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoginResult getResult() {
        return result;
    }

    public void setResult(LoginResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "{" +
                "\"user\":" + user.toString() +
                ", \"result\":" + result.toString() +
                '}';
    }
}
