import React from 'react';
import '../css/AuthPage.css';
import AuthForm from "../components/AuthForm";
import Footer from "../components/Footer";

function AuthPage() {
    return (
        <div className="auth-container">
            <div className="auth-content">
                <div className="auth-panel">
                    <AuthForm/>
                </div>
                <div className="title-logo">
                    <div className="title">Name of product</div>
                    Some slogan of product here!
                </div>

            </div>
            <Footer/>
        </div>
    );
}

export default AuthPage;