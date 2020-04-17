import React, {useState} from 'react';
import {connect, useDispatch, useSelector} from "react-redux";
import {postAuth} from "../../redux/auth/AuthService";
import {auth} from "../../redux/auth/AuthActions";
import {Redirect} from "react-router-dom";

const Authentication = ({isAuth}) => {

    const dispatch = useDispatch();

    const [authForm, setAuthForm] = useState({username:"", password:""});

    const submitHandler = async event => {
        const {username, password} = authForm;
        const authInfo = {username, password};
        const tokens = await postAuth(authInfo);
        dispatch(auth(tokens));
        setAuthForm({username: "", password: ""});
    };

    const changeInputHandler = event => {
        event.persist();
        setAuthForm(pervState => ( { ...pervState, ...{
            [event.target.id]: event.target.value
        }}))
    };

    return (
        <div className="authentication-container">
            {isAuth && <Redirect to="/profile"/>}
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

const mapStateToProps = state => {
    return {
        isAuth: state.auth.isAuth,
    }
};

export default connect(mapStateToProps, null)(Authentication);
