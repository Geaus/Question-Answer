import React, { useState } from 'react';
import { Input, Button, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import {login} from "../../service/LoginService"


const LoginCard = () => {

    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleLogin = () => {

        if(username==='' || password===''){
            message.error('用户名或密码不能为空');
            return;
        }

        login(username, password)
            .then((data) => {

                message.success('登录成功');
                const type=sessionStorage.getItem('type');
                    navigate('/');

            })
            .catch((error) => {
                message.error(error.message);
            });
    };

    return (
        <div>
            <div className="login-header" style={{ height: '56px', width: '300px' }}>
                <h2 style={{ textAlign: 'left', color: '#fff' }}>欢迎登录</h2>
            </div>

            <div >
                <div style={{ marginBottom: 16 }}>
                    <Input placeholder="用户名" value={username} onChange={handleUsernameChange} />
                </div>
                <div style={{ marginBottom: 16 }}>
                    <Input.Password placeholder="密码" value={password} onChange={handlePasswordChange} />
                </div>
                <div>
                    <Button  onClick={handleLogin}  style={{ float: 'right' }}>
                        登录
                    </Button>
                </div>
            </div>
        </div>
    );
};

export default LoginCard;
