import React, {useState} from 'react';
import {useDispatch} from "react-redux";
import {postRegister} from "../../redux/auth/AuthService";
import {register} from "../../redux/auth/AuthActions";

export const Registration = () => {

    const dispatch = useDispatch();

    const [registration, setRegistration] = useState({email:"", username:"", password:""});

    const submitHandler = async event => {
        const {email, username, password} = registration;
        const registrationInfo = {email, username, password};
        console.log(registrationInfo);
        const tokens =  await postRegister(registrationInfo);
        dispatch(register(tokens));
        setRegistration({email: "", username: "", password: ""});
    };

    const changeInputHandler = event => {
        event.persist();
        setRegistration(pervState => ( { ...pervState, ...{
                [event.target.id]: event.target.value
            }}))
    };

    return (
        <div className="registration-container">
            <h1 className="title">Registration</h1>
            <input
                type="text"
                id="username"
                className="auth-input"
                placeholder="Username"
                value={registration.username}
                onChange={changeInputHandler}
            />
            <input
                type="text"
                id="email"
                className="auth-input"
                placeholder="Email"
                value={registration.email}
                onChange={changeInputHandler}
            />
            <input
                type="password"
                id="password"
                className="auth-input"
                placeholder="Password"
                value={registration.password}
                onChange={changeInputHandler}
            />
            <button
                className="auth-button"
                type="submit"
                onClick={submitHandler}
            >Register</button>
        </div>
    );
};
