import React, {useState} from 'react';

export const Authentication = () => {

    const [login, setLogin] = useState('login');

    const [password, setPassword] = useState('password');

    const submitHandler = event => {
    };

    return (
        <div className="authentication-container">
            <h1 className="title">Authentication</h1>
            <input
                type="text"
                id="login"
                className="auth-input"
                value={login.valueOf()}
                onChange={event => setLogin(event.target.value)}
                onFocus={() => setLogin('')}
            />
            <input
                type="password"
                id="password"
                className="auth-input"
                value={password.valueOf()}
                onChange={event => setPassword(event.target.value)}
                onFocus={() => setPassword('')}
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