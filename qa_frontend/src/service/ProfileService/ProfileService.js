export const getAsked = (params,callback) => {

    fetch('http://localhost:8080/getAsked?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            token:'1231231'
        },
    })
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const getAnswered = (params,callback) => {

    fetch('http://localhost:8080/getAnswered?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            token:'1231231'
        },
    })
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const getLikedQuestion = (params,callback) => {

    fetch('http://localhost:8080/getLikedQuestion?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            token:'1231231'
        },
    })
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};


export const getDislikedQuestion = (params,callback) => {

    fetch('http://localhost:8080/getDislikedQuestion?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            token:'1231231'
        },
    })
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};


export const getMarkedQuestion = (params,callback) => {

    fetch('http://localhost:8080/getMarkedQuestion?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            token:'1231231'
        },
    })
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};


export const getLikedAnswer = (params,callback) => {

    fetch('http://localhost:8080/getLikedAnswer?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            token:'1231231'
        },
    })
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};


export const getDislikedAnswer = (params,callback) => {

    fetch('http://localhost:8080/getDislikedAnswer?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            token:'1231231'
        },
    })
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const getFollowed = (params,callback) => {

    fetch('http://localhost:8080/getFollowed?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            token:'1231231'
        },
    })
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const getUser = (params,callback) => {

    fetch('http://localhost:8080/getUser?'+params.toString() ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            token:'1231231'
        },
    })
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

