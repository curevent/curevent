import React from "react";
import {connect, useDispatch} from "react-redux";
import '../../css/header.css'
import {NavLink, Redirect} from "react-router-dom";
import {Search} from "./Search";
import UserMenu from "./UserMenu";

const Header = ({isAuth}) => {

    const dispatch = useDispatch();

    return (
        <div className="header-container">
            {!isAuth && <Redirect to="/"/>}
            <div className="header-content">
                <div className="header-logo">Curevent</div>
                <Search/>
                <div className="user-menu-position">
                    <UserMenu/>
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
