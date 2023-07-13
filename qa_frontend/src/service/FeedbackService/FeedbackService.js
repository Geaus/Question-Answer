
import {message} from "antd";
import {URL} from "../../App";

export const feedbackQuestion = (params,callback) => {

    fetch(URL+'/feedbackQuestion?'+params.toString() ,{
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

export const feedbackAnswer = (params,callback) => {

    fetch(URL+'/feedbackAnswer?'+params.toString() ,{
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

export const changeFollow = (params,callback) => {

    fetch(URL+'/changeFollow?'+params.toString() ,{
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