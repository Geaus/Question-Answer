import React, {useState} from 'react';
import {Input, Button, message, Card, Row, Col} from 'antd';
import {useNavigate} from "react-router-dom";
import {register} from "../../../service/LoginService/LoginService";


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


        const params = new URLSearchParams();
        params.append('userName', username);
        params.append('passWord',password);
        params.append('email',email);
        register(params, props.onSubmit);

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
            <Card style={cardStyle} title="注册" hoverable>
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
                <Input.Password
                    placeholder="重复密码"
                    style={{ marginBottom: 16 }}
                    value={rePassword}
                    onChange={(e) => setRePassword(e.target.value)}
                />
                <Input
                    placeholder="邮箱"
                    style={{ marginBottom: 16 }}
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <Row>
                    <Col span={12}>
                        <Button data-testid="register-submit-button" type="primary" style={{ marginRight: 8 }} onClick={handleSubmit}>
                            提交
                        </Button>
                    </Col>
                    <Col span={12}>
                        <Button  onClick={()=>{
                            const { onSubmit } = props;
                            onSubmit();

                        }}  style={{ float: 'right' }}
                        >
                            取消
                        </Button>
                    </Col>
                </Row>
            </Card>
        </div>
    );
}

export default RegisterForm;

