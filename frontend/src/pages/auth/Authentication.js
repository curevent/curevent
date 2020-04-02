import React, {useState} from 'react';
import {useDispatch} from "react-redux";
import {postAuth} from "../../redux/actions/AuthActions";

const Authentication = () => {

    const dispatch = useDispatch()

    const [auth, setAuth] = useState({username:"", password:""});

    const submitHandler = event => {
        const {username, password} = auth;
        const authInfo = {username, password};
        console.log(authInfo);
        dispatch(postAuth(authInfo));
        setAuth({username:"", password:""});
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
                id="username"
                className="auth-input"
                placeholder="login"
                value={auth.username}
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

export default Authentication;