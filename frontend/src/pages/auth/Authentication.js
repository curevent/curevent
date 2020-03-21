import React from 'react';
import '../../css/authentication.css'

export const Authentication = () => {
    return (
        <div className="authentication-container">
            <input id="login" className="auth-input"/>
            <input id="password" className="auth-input"/>
            <button className="auth-button" type="submit">Login</button>
        </div>
    );
};