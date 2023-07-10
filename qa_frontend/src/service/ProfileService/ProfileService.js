import {message} from "antd";

export const getAsked = (params,callback) => {

    fetch('http://localhost:8080/getAsked?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            if(data != null && data.code != null && data.code === 401) {
                window.location.pathname = '/login'
                message.error("用户认证失败，请重新登录")
            }
            else if(data != null && data.code != null && data.code === 403) {
                if(window.location.pathname !== '/')window.location.pathname = '/'
                else window.location.pathname = '/login'
                message.error("用户权限错误")
            }
            else callback(data);
        })
};

export const getAnswered = (params,callback) => {

    fetch('http://localhost:8080/getAnswered?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            if(data != null && data.code != null && data.code === 401) {
                window.location.pathname = '/login'
                message.error("用户认证失败，请重新登录")
            }
            else if(data != null && data.code != null && data.code === 403) {
                if(window.location.pathname !== '/')window.location.pathname = '/'
                else window.location.pathname = '/login'
                message.error("用户权限错误")
            }
            else callback(data);
        })
};

export const getLikedQuestion = (params,callback) => {

    fetch('http://localhost:8080/getLikedQuestion?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            if(data != null && data.code != null && data.code === 401) {
                window.location.pathname = '/login'
                message.error("用户认证失败，请重新登录")
            }
            else if(data != null && data.code != null && data.code === 403) {
                if(window.location.pathname !== '/')window.location.pathname = '/'
                else window.location.pathname = '/login'
                message.error("用户权限错误")
            }
            else callback(data);
        })
};


export const getDislikedQuestion = (params,callback) => {

    fetch('http://localhost:8080/getDislikedQuestion?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            if(data != null && data.code != null && data.code === 401) {
                window.location.pathname = '/login'
                message.error("用户认证失败，请重新登录")
            }
            else if(data != null && data.code != null && data.code === 403) {
                if(window.location.pathname !== '/')window.location.pathname = '/'
                else window.location.pathname = '/login'
                message.error("用户权限错误")
            }
            else callback(data);
        })
};


export const getMarkedQuestion = (params,callback) => {

    fetch('http://localhost:8080/getMarkedQuestion?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            if(data != null && data.code != null && data.code === 401) {
                window.location.pathname = '/login'
                message.error("用户认证失败，请重新登录")
            }
            else if(data != null && data.code != null && data.code === 403) {
                if(window.location.pathname !== '/')window.location.pathname = '/'
                else window.location.pathname = '/login'
                message.error("用户权限错误")
            }
            else callback(data);
        })
};


export const getLikedAnswer = (params,callback) => {

    fetch('http://localhost:8080/getLikedAnswer?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            if(data != null && data.code != null && data.code === 401) {
                window.location.pathname = '/login'
                message.error("用户认证失败，请重新登录")
            }
            else if(data != null && data.code != null && data.code === 403) {
                if(window.location.pathname !== '/')window.location.pathname = '/'
                else window.location.pathname = '/login'
                message.error("用户权限错误")
            }
            else callback(data);
        })
};


export const getDislikedAnswer = (params,callback) => {

    fetch('http://localhost:8080/getDislikedAnswer?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            if(data != null && data.code != null && data.code === 401) {
                window.location.pathname = '/login'
                message.error("用户认证失败，请重新登录")
            }
            else if(data != null && data.code != null && data.code === 403) {
                if(window.location.pathname !== '/')window.location.pathname = '/'
                else window.location.pathname = '/login'
                message.error("用户权限错误")
            }
            else callback(data);
        })
};

export const getFollowed = (params,callback) => {

    fetch('http://localhost:8080/getFollowed?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            if(data != null && data.code != null && data.code === 401) {
                window.location.pathname = '/login'
                message.error("用户认证失败，请重新登录")
            }
            else if(data != null && data.code != null && data.code === 403) {
                if(window.location.pathname !== '/')window.location.pathname = '/'
                else window.location.pathname = '/login'
                message.error("用户权限错误")
            }
            else callback(data);
        })
};

export const getUser = (params,callback) => {

    fetch('http://localhost:8080/getUser?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            if(data != null && data.code != null && data.code === 401) {
                window.location.pathname = '/login'
                message.error("用户认证失败，请重新登录")
            }
            else if(data != null && data.code != null && data.code === 403) {
                if(window.location.pathname !== '/')window.location.pathname = '/'
                else window.location.pathname = '/login'
                message.error("用户权限错误")
            }
            else callback(data);
        })
};

