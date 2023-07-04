import {message} from "antd";

export const login = (username, password) => {

    return fetch('http://localhost:8080/login1', {
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

            if(data.id===-1){

                throw Error("用户名密码错误")
            }

            sessionStorage.setItem('uid', data.id);
            if(data.type===-1){
                throw Error("用户已被封禁")
            }
            sessionStorage.setItem('type', data.type);

        });
};

export const newUser = (params,callback) => {

    fetch('http://localhost:8080/newUser?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};
