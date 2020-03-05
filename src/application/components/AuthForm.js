import React, { Component } from 'react';

class AuthForm extends Component {
    render() {
        return (
            <div className="auth-form">
                <div className="auth-header">
                    <div className="button">
                        Sign-in
                    </div>
                    <div className="button sign-up_button">
                        Sign-up
                    </div>
                </div>
                <input id="login" className="auth-input" type="text"></input>
                <input id="password" className="auth-input" type="password"></input>
                <div className="auth-bottom">
                    <button className="button login_button">Login</button>
                    <a className="password-recovery" href="">Forget password?</a>
                </div>
            </div>
        );
    }
}

export default AuthForm;