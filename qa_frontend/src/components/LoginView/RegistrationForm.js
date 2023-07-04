import React, {useState} from 'react';
import {Input, Button, message, Card, Row, Col} from 'antd';
import {useNavigate} from "react-router-dom";
import {register} from "../../service/LoginService";


function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function RegisterForm (props) {
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
        register(username, password, email)
            .then(() => {
                message.success('注册成功');
                props.setShowRegistrationForm(false);
            }).catch((error) => {
                message.error(error.message);
            });
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
                <Row>
                    <Col span={12}>
                        <Button type="primary" style={{ marginRight: 8 }} onClick={handleSubmit}>
                            提交
                        </Button>
                    </Col>
                    <Col span={6}>
                        <Button  onClick={()=>{
                            props.setShowRegistrationForm(false);
                        }}  style={{ float: 'right' }}
                        >
                            cancel
                        </Button>
                    </Col>
                </Row>
            </Card>
        </div>
    );
}

export default RegisterForm;

