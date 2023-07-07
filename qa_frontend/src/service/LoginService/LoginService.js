import {message} from "antd";

export const login = (username, password) => {

    return fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `userName=${username}&passWord=${password}`,
    })
        .then((response) => {
            if (response.ok) {

                return response.json();
            } else {
                throw new Error('用户名或密码错误');
            }
        })
        .then((data) => {
            console.log(data);
            if(data.code != null && data.code === 401) {
                throw Error("用户名或密码错误");
            }
            else {
                if(data.user.id === -1) {
                    throw Error(data.result.token);
                }
                else {
                    sessionStorage.setItem('uid', data.user.id);
                    sessionStorage.setItem('token', data.result.token)
                }
            }
        });
};

export const register = (params, callback) => {

    fetch('http://localhost:8080/register?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
    })
        .then(response => response.json())
        .then(data => callback(data))
};

export const logout = (params, callback) => {
    fetch('http://localhost:8080/logoutSystem?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(() => {
            callback();
        })
}