import React, {useState} from 'react';
import '../../css/authentication.css'
import {Registration} from "./Registration";
import {Redirect} from "react-router-dom";
import {connect} from "react-redux";
import {Authentication} from "./Authentication";

const AuthPage = ({isAuth}) => {

    const [authComponent, setAuthComponent] = useState({name:"auth", swap_to:"Sign-up"});

    const swapHandler = event => {
        if (authComponent.name === "auth") {
            setAuthComponent({name: "reg", swap_to: "Sign-in"})
        } else {
            setAuthComponent({name: "auth", swap_to: "Sign-up"})
        }
    };

    return (
        <div className="auth-container">
            <div className="auth-form">
                {isAuth && <Redirect to="/profile"/>}
                <button className="swap-button" onClick={swapHandler}>{authComponent.swap_to}</button>
                {authComponent.name==="auth" && <Authentication/>}
                {authComponent.name==="reg" && <Registration/>}
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



