import React, {useState} from 'react';
import {Button, message} from "antd";
import LoginCard from "../../components/LoginView/LoginCard/LoginCard";
import RegistrationForm from "../../components/LoginView/RegistrationForm/RegistrationForm";
import {Content} from "antd/es/layout/layout";
import picture from "../../components/LoginView/20a75d1dcb2b02902388ef9078a7fc5f2dad1e46_2_1035x690.jpeg"


function LoginView () {

    const [showRegistrationForm,setShowRegistrationForm] =useState(false);

    const handleRegistrationSubmit = (data) => {
        if(data != null && data.id === -1) {
            message.error(data.userName);
        }
        else if(data == null) {
            setShowRegistrationForm(false);
        }
        else {
            message.success("注册成功")
            setShowRegistrationForm(false);
        }
    };

    return (
        <div style={{
            backgroundImage: `url(${picture})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            backgroundRepeat: 'no-repeat',
        }}>
            <Content
                style={{
                    height: '100vh',
                    width:'80vw',
                    margin: '0 auto',
                    paddingTop:'3vh',
                    alignItems: 'center',
                    justifyContent: 'center',
                }}
            >
                        {!showRegistrationForm && (
                            <LoginCard setShowRegistrationForm={setShowRegistrationForm}/>
                        )}
                        {showRegistrationForm && (
                            <RegistrationForm onSubmit={handleRegistrationSubmit} />
                        )}
            </Content>
        </div>
    );

}

export default LoginView;