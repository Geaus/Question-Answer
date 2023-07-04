package com.example.qa_backend.JSON;

public class LoginResult {
    private int code;
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"token\":\"" + token + '\"' +
                '}';
    }
}
