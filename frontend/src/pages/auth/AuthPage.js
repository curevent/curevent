import React, {useState} from 'react';
import '../../css/authentication.css'
import {Registration} from "./Registration";
import {SwapButton} from "../../components/buttons/SwapButton";
import Authentication from "./Authentication";

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



