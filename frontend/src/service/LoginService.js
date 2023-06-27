export const login = (username, password) => {

    return fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `username=${username}&password=${password}`,
    })
        .then((response) => {
            if (response.ok) {

                return response.json();
            } else {
                throw new Error('用户名或密码错误');
            }
        })
        .then((data) => {

            if(data.isBlack.toString()==='1'){
                throw new Error('用户被封禁');
            }
            sessionStorage.setItem('uid', data.id);
            sessionStorage.setItem('type',data.userType)
            return data;
        });
};

export const newUser = (params,callback) => {

    fetch('http://localhost:8080/newUser?'+params.toString())
        .then(response => response.json())
        .then((data) => {
            callback(data);
        })
};
