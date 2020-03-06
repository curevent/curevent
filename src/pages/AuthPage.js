import React from 'react';
import '../css/AuthPage.css';
import '../css/components/AuthForm.css';
import AuthForm from "../components/AuthForm";

function AuthPage() {
    return (
        <div className="auth-container">
            <div className="auth-panel">
                <AuthForm/>
            </div>
            <div className="title-logo">
                <div className="title">Name of product</div>
                <div className="slogan">Some slogan of product here!</div>
            </div>
        </div>
    );
}

export default AuthPage;