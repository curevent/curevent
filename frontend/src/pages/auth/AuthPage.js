import React, {useState} from 'react';
import '../../css/authentication.css'
import {Authentication} from "./Authentication";
import {Registration} from "./Registration";
import {SwapButton} from "../../components/buttons/SwapButton";

export const AuthPage = () => {

    const [authComponent, setAuthComponent] = useState({name:"auth"});

    return (
        <div className="page-auth-container">
            <SwapButton/>
            <SwapButton/>
            {authComponent.name==="auth" && <Authentication/>}
            {authComponent.name==="reg" && <Registration/>}
        </div>
    );
};



