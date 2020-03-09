import React from 'react';
import '../commons/components/css/AuthPage.css';
import '../commons/components/css/components/AuthForm.css';
import AuthForm from "./AuthForm";

export default function LandingPage() {
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