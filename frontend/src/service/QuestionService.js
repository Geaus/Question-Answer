export const getQuestions = (callback) => {

    fetch('http://localhost:8080/getQuestions')
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};

export const getQuestion = (params,callback) => {

    fetch('http://localhost:8080/getQuestion?'+params.toString())
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

export const askQuestion = (params, tags, callback) => {
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
        .then((data) => {callback(data)})

}