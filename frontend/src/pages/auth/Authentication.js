import React, {useState} from 'react';

export const Authentication = () => {

    const [auth, setAuth] = useState({login:"", password:""});

    const submitHandler = event => {
        console.log(document.getElementById("login"));
        console.log(document.getElementById("password"));
    };

    const changeInputHandler = event => {
        event.persist();
        setAuth(pervState => ( { ...pervState, ...{
            [event.target.id]: event.target.value
        }}))
    };

    return (
        <div className="authentication-container">
            <h1 className="title">Authentication</h1>
            <input
                type="text"
                id="login"
                className="auth-input"
                placeholder="login"
                value={auth.login}
                onChange={changeInputHandler}
            />
            <input
                type="password"
                id="password"
                className="auth-input"
                placeholder="password"
                value={auth.password}
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