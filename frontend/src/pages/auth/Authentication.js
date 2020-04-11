import React, {useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {getWhoAmI, postAuth} from "../../redux/actions/AuthActions";

const Authentication = () => {

    const dispatch = useDispatch();

    const [authForm, setAuthForm] = useState({username:"", password:""});

    const submitHandler = event => {
        const {username, password} = authForm;
        const authInfo = {username, password};
        dispatch(postAuth(authInfo));
        setAuthForm({username:"", password:""});
    };

    const changeInputHandler = event => {
        event.persist();
        setAuthForm(pervState => ( { ...pervState, ...{
            [event.target.id]: event.target.value
        }}))
    };

    return (
        <div className="authentication-container">
            <h1 className="title">Authentication</h1>
            <input
                type="text"
                id="username"
                className="auth-input"
                placeholder="Username"
                value={authForm.username}
                onChange={changeInputHandler}
            />
            <input
                type="password"
                id="password"
                className="auth-input"
                placeholder="Password"
                value={authForm.password}
                onChange={changeInputHandler}
            />
            <button
                className="auth-button"
                type="submit"
                onClick={submitHandler}
            >
                Login
            </button>
        </div>
    );
};

export default Authentication;