export const getAsked = (params,callback) => {

    fetch('http://localhost:8080/getAsked?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const getAnswered = (params,callback) => {

    fetch('http://localhost:8080/getAnswered?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const getLikedQuestion = (params,callback) => {

    fetch('http://localhost:8080/getLikedQuestion?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};


export const getDislikedQuestion = (params,callback) => {

    fetch('http://localhost:8080/getDislikedQuestion?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};


export const getMarkedQuestion = (params,callback) => {

    fetch('http://localhost:8080/getMarkedQuestion?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};


export const getLikedAnswer = (params,callback) => {

    fetch('http://localhost:8080/getLikedAnswer?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};


export const getDislikedAnswer = (params,callback) => {

    fetch('http://localhost:8080/getDislikedAnswer?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const getFollowed = (params,callback) => {

    fetch('http://localhost:8080/getFollowed?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};
