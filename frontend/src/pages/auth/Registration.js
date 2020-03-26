import React from 'react';

export const Registration = () => {
    return (
        <div className="registration-container">
            <h1 className="title">Registration</h1>
            <input type="text" id="login" className="auth-input"/>
            <input type="text" id="email" className="auth-input"/>
            <input type="password" id="password" className="auth-input"/>
            <button className="auth-button" type="submit">Register</button>
        </div>
    );
};