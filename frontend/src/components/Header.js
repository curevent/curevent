import React from "react";
import {connect, useDispatch} from "react-redux";
import '../css/header.css'
import {invalidateAuth} from "../redux/actions/AuthActions";
import {invalidateLocalStorage} from "../utils/localStorageUtils";
import {NavLink, Redirect} from "react-router-dom";

const Header = ({isAuth}) => {

    const AUTH_PAGE = "http://localhost:3000/";

    const dispatch = useDispatch();

    const logoutHandler = (ignore) => {
        invalidateLocalStorage();
        dispatch(invalidateAuth);
        window.location.replace(AUTH_PAGE);
    };


    const logoutButton = () => {
        return <button className="header-button" onClick={logoutHandler}>Sign-out</button>
    };

    return (
        <div className="header-container">
            <div className="header-content">
                <div className="header-logo">Curevent</div>
                <div className="nav-menu">
                    <NavLink className="nav-button" to="/profile">Profile</NavLink>
                    <NavLink className="nav-button" to="/friends">Friends</NavLink>
                    <NavLink className="nav-button" to="/timeline">Timeline</NavLink>
                    <NavLink className="nav-button" to="/templates">Templates</NavLink>
                </div>
                <div className="header-auth-panel">
                    {!isAuth && <Redirect to="/"/>}
                    {isAuth && logoutButton()}
                </div>
            </div>
        </div>
    );
};

const mapStateToProps = state => {
    return {
        isAuth: state.auth.isAuth,
    }
};

export default connect(mapStateToProps, null)(Header);
