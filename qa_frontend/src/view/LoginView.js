import React, {useState} from 'react';
import {Button} from "antd";
import LoginCard from "../components/LoginView/LoginCard";
import RegistrationForm from "../components/LoginView/RegistrationForm";


function LoginView () {

    const [showRegistrationForm,setShowRegistrationForm] =useState(false);

    const handleRegistrationSubmit = () => {
       setShowRegistrationForm(false);
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