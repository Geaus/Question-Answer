
export const feedbackQuestion = (params,callback) => {

    fetch('http://localhost:8080/feedbackQuestion?'+params.toString() ,{
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

export const feedbackAnswer = (params,callback) => {

    fetch('http://localhost:8080/feedbackAnswer?'+params.toString() ,{
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

export const changeFollow = (params,callback) => {

    fetch('http://localhost:8080/changeFollow?'+params.toString() ,{
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