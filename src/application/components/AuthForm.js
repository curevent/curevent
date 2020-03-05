import React, { Component } from 'react';

class AuthForm extends Component {
    render() {
        return (
            <div className="auth-form">
                <div className="auth-header">
                    <div className="button sign-in_button">
                        Sign-in
                    </div>
                    <div className="button">
                        Sign-up
                    </div>
                </div>
                <input id="login" className="auth-input" type="text"></input>
                <input id="password" className="auth-input" type="password"></input>
                <button className="button login_button">Login</button>
            </div>
        );
    }
}

export default AuthForm;