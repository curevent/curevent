import React from "react";
import {connect, useDispatch} from "react-redux";
import '../css/header.css'
import {invalidateAuth} from "../redux/actions/AuthActions";
import {invalidateLocalStorage} from "../utils/localStorageUtils";

const Header = ({isAuth}) => {

    const dispatch = useDispatch();

    const logoutHandler = (ignore) => {
        invalidateLocalStorage();
        dispatch(invalidateAuth);
        window.location.replace( window.location.href);
    };

    const logoutButton = () => {
        return <button className="logout-button" onClick={logoutHandler}>Sign-out</button>
    };

    return (
        <div className="header-container">
            <div className="header-content">
                <div className="header-logo">Curevent</div>
                {isAuth && logoutButton()}
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
