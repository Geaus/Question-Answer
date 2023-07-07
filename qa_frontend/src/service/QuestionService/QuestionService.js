export const getQuestions = (params,callback) => {

    fetch('http://localhost:8080/getQuestions?'+params.toString(),{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const getQuestion = (params,callback) => {

    fetch('http://localhost:8080/findQuestion?'+params.toString(),{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const getAnswers = (params,callback) => {

    fetch('http://localhost:8080/getAnswer?'+params.toString(),{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const listTag = (callback) => {

    fetch('http://localhost:8080/listTag' ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then((response) => response.json())
        .then((data) => {callback(data)})

}
export const askQuestion = (params, tags,callback) => {
    // const params = new URLSearchParams();
    // let uid = sessionStorage.getItem('uid');
    // params.append('userName', username);

    // params.append('uid', uid);
    fetch('http://localhost:8080/askQuestion?' + params.toString(), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'token' : sessionStorage.getItem('token'),
        },
        body: JSON.stringify(tags)

    })
        .then((response) => response.json())
        .then((data) => {callback(data)})

}

export const addAnswer = (params) => {

    fetch('http://localhost:8080/addAnswer?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            console.log(data);
        })
}

export const searchQuestion = (params,callback) => {

    fetch('http://localhost:8080/searchByTitle?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const deleteQuestion = (params,callback) => {

    fetch('http://localhost:8080/deleteQuestion?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(callback)

};

export const deleteAnswer = (params,callback) => {

    fetch('http://localhost:8080/deleteAnswer?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(callback)

};

export const banUser= (params,callback) => {

    fetch('http://localhost:8080/banUser?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'token' : sessionStorage.getItem('token'),
        },
    })
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};
