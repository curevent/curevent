import React from "react";
import {connect, useDispatch} from "react-redux";
import '../css/header.css'
import {invalidateAuth} from "../redux/actions/AuthActions";
import {invalidateLocalStorage} from "../utils/localStorageUtils";
import {NavLink, Redirect} from "react-router-dom";
import {Search} from "./Search";
import {CreateTemplate} from "./CreateTemplate";

const Header = ({isAuth}) => {

    const AUTH_PAGE = "http://localhost:3000/";

    const dispatch = useDispatch();

    const logoutHandler = (ignore) => {
        invalidateLocalStorage();
        dispatch(invalidateAuth);
        window.location.replace(AUTH_PAGE);
    };

    return (
        <div className="header-container">
            {!isAuth && <Redirect to="/"/>}
            <div className="header-content">
                <div className="header-logo">Curevent</div>
                <div className="nav-menu">
                    <NavLink className="nav-button" to="/profile">Profile</NavLink>
                    <NavLink className="nav-button" to="/friends">Friends</NavLink>
                    <NavLink className="nav-button" to="/timeline">Timeline</NavLink>
                    <NavLink className="nav-button" to="/settings">Settings</NavLink>
                </div>
                <Search/>
                <CreateTemplate/>
                <button className="header-button shadow-none" onClick={logoutHandler}>Sign-out</button>
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
