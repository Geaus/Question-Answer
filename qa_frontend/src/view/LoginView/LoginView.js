import React, {useState} from 'react';
import {Button, message} from "antd";
import LoginCard from "../../components/LoginView/LoginCard/LoginCard";
import RegistrationForm from "../../components/LoginView/RegistrationForm/RegistrationForm";


function LoginView () {

    const [showRegistrationForm,setShowRegistrationForm] =useState(false);
    
    const handleRegistrationSubmit = (data) => {
        if(data != null && data.id === -1) {
            message.error(data.userName);
        }
        else setShowRegistrationForm(false);
    };

    return (
        <div className="login-page">
            <div className="login-container">
                <div className="login-box">

                    <div className="login-content">
                        <LoginCard />
                        {showRegistrationForm && (
                            <RegistrationForm onSubmit={handleRegistrationSubmit} />
                        )}
                        {!showRegistrationForm && (
                            <Button onClick={() => setShowRegistrationForm(true)}>
                                Register
                            </Button>
                        )}
                    </div>

                </div>
            </div>
        </div>
    );

}

export default LoginView;