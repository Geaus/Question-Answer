import React from 'react';
import {Input, Button, message} from 'antd';
import {newUser} from "../../service/LoginService"


function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

class RegisterForm extends React.Component {

    constructor() {
        super();
        this.state={
            username:"",
            password:"",
            rePassword:"",
            email:""
        }
    }
    handleSubmit = () => {
        // Do something when the form is submitted
        const { onSubmit } = this.props;

        if(this.state.username==="" || this.state.password==="" ||this.state.email===""){
            message.error("信息不能为空");
            return;
        }
        if(this.state.password!==this.state.rePassword){
            message.error("两次输入密码不相同");
            return;
        }

        if(!validateEmail(this.state.email)){
            message.error("邮箱格式不正确");
            return;
        }

        const params = new URLSearchParams();
        params.append('username', this.state.username);
        params.append('password', this.state.password);
        params.append('email', this.state.email);

        console.log(this.state.username);

        const callback =  (data) => {

            console.log(data)
            if(data.toString()==='0'){
                message.error('用户名重复');
            }
            else if(data.toString()==='1'){
                message.success('用户注册成功');
                onSubmit();
            }

        };

        newUser(params,callback);


    };

    handleUsername=(event)=>{
        this.setState({username:event.target.value});
    }

    handlePassword=(event)=>{
        this.setState({password:event.target.value})
    }

    handleRePassword=(event)=>{
        this.setState({rePassword:event.target.value})
    }

    handleEmail=(event)=>{
        this.setState({email:event.target.value})
    }

    render() {
        return (
            <div>

                <div style={{ marginBottom: 16 }}>
                    <label htmlFor="username" style={{color:'white'}}>用户名:</label>
                    <Input id="用户名" value={this.state.username} onChange={this.handleUsername}/>
                </div>

                <div style={{ marginBottom: 16 }}>
                    <label htmlFor="password" style={{color:'white'}}>密码:</label>
                    <Input id="密码" type="password" value={this.state.password} onChange={this.handlePassword}/>
                </div>

                <div style={{ marginBottom: 16 }}>
                    <label htmlFor="password" style={{color:'white'}}>重复密码:</label>
                    <Input id="重复密码" type="password" value={this.state.rePassword} onChange={this.handleRePassword}/>
                </div>

                <div style={{ marginBottom: 16 }}>
                    <label htmlFor="password" style={{color:'white'}}>邮箱:</label>
                    <Input id="邮箱"  value={this.state.email} onChange={this.handleEmail}/>
                </div>

                <Button  onClick={this.handleSubmit}>
                    Register
                </Button>
                <Button  onClick={()=>{
                    const { onSubmit } = this.props;
                    onSubmit();

                }}  style={{ float: 'right' }}
                >
                    cancel
                </Button>
            </div>
        );
    }
}

export default RegisterForm;

