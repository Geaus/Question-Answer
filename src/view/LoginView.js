import React from 'react';
import {Button} from "antd";
import LoginCard from "../components/LoginView/LoginCard";
import RegistrationForm from "../components/LoginView/RegistrationForm";


class LoginView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showRegistrationForm: false
        };
    }

    handleRegistrationSubmit = () => {
        this.setState({ showRegistrationForm: false });
    };

    render() {
        return (
            <div className="login-page">
                <div className="login-container">
                    <div className="login-box">

                        <div className="login-content">
                            <LoginCard />
                            {this.state.showRegistrationForm && (
                                <RegistrationForm onSubmit={this.handleRegistrationSubmit} />
                            )}
                            {!this.state.showRegistrationForm && (
                                <Button onClick={() => this.setState({ showRegistrationForm: true })}>
                                    Register
                                </Button>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default LoginView;