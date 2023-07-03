export const getQuestions = (params,callback) => {

    fetch('http://localhost:8080/getQuestions?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const getQuestion = (params,callback) => {

    fetch('http://localhost:8080/findQuestion?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const getAnswers = (params,callback) => {

    fetch('http://localhost:8080/getAnswer?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const listTag = (callback) => {

    fetch('http://localhost:8080/listTag' )
        .then((response) => response.json())
        .then((data) => {callback(data)})

}
export const askQuestion = (params, tags) => {
    let opts = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(tags)
    }
    // const params = new URLSearchParams();
    // let uid = sessionStorage.getItem('uid');
    // params.append('userName', username);

    // params.append('uid', uid);
    fetch('http://localhost:8080/askQuestion?' + params.toString(), opts)
        .then((response) => response.json())
        .then((data) => {console.log(data)})

}

export const addAnswer = (params) => {
    fetch('http://localhost:8080/addAnswer?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            console.log(data);
        })
}

export const searchQuestion = (params,callback) => {

    fetch('http://localhost:8080/searchByTitle?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const deleteQuestion = (params,callback) => {

    fetch('http://localhost:8080/deleteQuestion?'+params.toString())
        .then(callback)

};

export const deleteAnswer = (params,callback) => {

    fetch('http://localhost:8080/deleteAnswer?'+params.toString())
        .then(callback)

};

export const banUser= (params,callback) => {

    fetch('http://localhost:8080/banUser?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};
