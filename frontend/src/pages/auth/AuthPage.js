import React, {useEffect, useState} from 'react';
import '../../css/authentication.css'
import {Registration} from "./Registration";
import {Redirect} from "react-router-dom";
import {connect} from "react-redux";
import {Authentication} from "./Authentication";

const AuthPage = ({isAuth}) => {

    const [authComponent, setAuthComponent] = useState({name:"auth"});

    let leftSwapButton;
    let rightSwapButton;

    useEffect(() => {
        leftSwapButton = window.document.getElementById("left-swap");
        rightSwapButton = window.document.getElementById("right-swap");
    });

    const swapAuthHandler = event => {
        leftSwapButton.disabled = false;
        rightSwapButton.disabled = true;
        setAuthComponent({name: "reg"})
    };

    const swapRegHandler = event => {
        leftSwapButton.disabled = true;
        rightSwapButton.disabled = false;
        setAuthComponent({name: "auth"})
    };

    return (
        <div className="auth-container">
            <div className="swap-panel">
                <button id="left-swap" className="swap-button" onClick={swapRegHandler}>Sign-in</button>
                <button id="right-swap" className="swap-button" onClick={swapAuthHandler}>Sign-up</button>
            </div>
            <div className="auth-form">
                {isAuth && <Redirect to="/profile"/>}
                {authComponent.name === "auth" && <Authentication/>}
                {authComponent.name === "reg" && <Registration/>}
            </div>
        </div>
    );
};

const mapStateToProps = state => {
    return {
        isAuth: state.auth.isAuth,
    }
};

export default connect(mapStateToProps, null)(AuthPage);



