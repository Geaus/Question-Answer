import React, { useState } from 'react';
import {Input, Button, message, Card} from 'antd';
import { useNavigate } from 'react-router-dom';
import {login} from "../../../service/LoginService/LoginService"


const LoginCard = (props) => {

    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = () => {

        if(username==='' || password===''){
            message.error('用户名或密码不能为空');
            return;
        }

        login(username, password)
            .then((data) => {
                message.success('登录成功');
                    navigate('/');
            })
            .catch((error) => {
                message.error(error.message);
            });
    };

    const cardStyle = {
        width: 400,
        margin: 'auto',
        marginTop: 200,
        padding: 20,
        textAlign: 'center',
    };

    return (
        <div>
            <Card style={cardStyle} title="欢迎登陆！" hoverable>
                <Input
                    placeholder="用户名"
                    style={{ marginBottom: 16 }}
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <Input.Password
                    placeholder="密码"
                    style={{ marginBottom: 16 }}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <Button
                    data-testid="login-button"
                    type="primary"
                    style={{ marginRight: 8 }}
                    onClick={handleLogin}
                >
                    登录
                </Button>
                <Button data-testid="register-show-button" onClick={() => props.setShowRegistrationForm(true)}>
                    注册
                </Button>
            </Card>
        </div>
    );
};

export default LoginCard;
