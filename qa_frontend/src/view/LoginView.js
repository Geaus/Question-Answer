import React, {useState} from 'react';
import {Button, Layout, Result} from "antd";
import LoginCard from "../components/LoginView/LoginCard";
import RegistrationForm from "../components/LoginView/RegistrationForm";
import HeaderTest from "../components/HomeView/HeaderTest";
import {Content, Header} from "antd/es/layout/layout";
import picture from '../components/LoginView/20a75d1dcb2b02902388ef9078a7fc5f2dad1e46_2_1035x690.jpeg'


function LoginView () {

    const [showRegistrationForm,setShowRegistrationForm] =useState(false);

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
                    <RegistrationForm setShowRegistrationForm={setShowRegistrationForm} />
                )}
            </Content>
        </div>
    );

}

export default LoginView;