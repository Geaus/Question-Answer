import React, {useState} from 'react';
import {Input, Button, message, Card} from 'antd';
import {useNavigate} from "react-router-dom";


function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function RegisterForm () {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [rePassword, setRePassword] = useState('');
    const [email, setEmail] = useState('');

    function handleSubmit() {
        if(username === "" || password==="" ||email===""){
            message.error("信息不能为空");
            return;
        }
        if(password!==rePassword){
            message.error("两次输入密码不相同");
            return;
        }

        if(!validateEmail(email)){
            message.error("邮箱格式不正确");
            return;
        }

        console.log(username);
    }

    function handleCancel() {
        navigate('/login')
    }

    const cardStyle = {
        width: 400,
        margin: 'auto',
        marginTop: 200,
        padding: 20,
        textAlign: 'center',
    };

    return (
        <div>
            <Card style={cardStyle} title="Register">
                <Input
                    placeholder="Username"
                    style={{ marginBottom: 16 }}
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <Input.Password
                    placeholder="Password"
                    style={{ marginBottom: 16 }}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <Input.Password
                    placeholder="Repeat Password"
                    style={{ marginBottom: 16 }}
                    value={rePassword}
                    onChange={(e) => setRePassword(e.target.value)}
                />
                <Input
                    placeholder="Email"
                    style={{ marginBottom: 16 }}
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <Button type="primary" style={{ marginRight: 8 }} onClick={handleSubmit}>
                    提交
                </Button>
                <Button onClick={handleCancel}>取消</Button>
            </Card>
        </div>
    );
}

export default RegisterForm;
