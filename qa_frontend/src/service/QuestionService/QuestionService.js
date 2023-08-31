import {useNavigate} from "react-router-dom";
import {message} from "antd";
import {URL} from "../../App";
import {json} from "react-router";
export const getQuestions = (params,callback) => {
    fetch(URL+'/getQuestions?'+params.toString(),{
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

export const getQuestion = (params,callback) => {

    fetch(URL+'/findQuestion?'+params.toString(),{
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

export const getAnswers = (params,callback) => {

    fetch(URL+'/getAnswer?'+params.toString(),{
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

export const listTag = (callback) => {
    const params = new URLSearchParams();
    params.append('uid', sessionStorage.getItem('uid'));
    fetch(URL+'/listTag?' + params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then((response) => response.json())
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
            else callback(data)
        })

}
export const askQuestion = (params, tags,callback) => {
    // const params = new URLSearchParams();
    // let uid = sessionStorage.getItem('uid');
    // params.append('userName', username);

    // params.append('uid', uid);
    fetch(URL+'/askQuestion?' + params.toString(), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'token' : sessionStorage.getItem('token'),
        },
        body: JSON.stringify(tags)

    })
        .then((response) => response.json())
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
            else if(data != null && data.id === -1) {
                message.error(data.content);
            }
            else callback(data)
        })

}

export const addAnswer = (params,content) => {

    fetch(URL+'/addAnswer?'+params.toString() ,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'token' : sessionStorage.getItem('token'),
        },
        body: content
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
            else if(data != null && data.id === -1) {
                message.error(data.content)
            }
            else window.location.reload();
        })
}

export const searchQuestion = (params,callback) => {

    fetch(URL+'/fullTextSearch?'+params.toString() ,{
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

export const deleteQuestion = (params,callback) => {

    fetch(URL+'/deleteQuestion?'+params.toString() ,{
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
            else callback();
        })

};

export const deleteAnswer = (params,callback) => {

    fetch(URL+'/deleteAnswer?'+params.toString() ,{
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
            else callback()
        })

};

export const banUser= (params,callback) => {

    fetch(URL+'/banUser?'+params.toString() ,{
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

export const searchQuestionByTag = (params, callback) => {
    fetch(URL+'/searchByTag?'+params.toString(), {
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
}

export const getHotQuestion = (params, callback) => {
    fetch(URL+'/hotQuestion?'+params.toString(),{
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
}
