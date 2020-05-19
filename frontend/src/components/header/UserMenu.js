import React, {Component} from "react";
import {getWhoAmI} from "../../redux/services/AuthService";
import {currentUser, invalidateAuth} from "../../redux/actions/AuthActions";
import {connect, useDispatch} from "react-redux";
import {UserImage} from "../fields/UserImage";
import {CreateTemplate} from "./CreateTemplate";
import {invalidateLocalStorage} from "../../utils/localStorageUtils";
import {NavLink} from "react-router-dom";
import {CreateEvent} from "./CreateEvent";
import {getUser} from "../../redux/services/UserService";

class UserMenu extends Component {

    state = {
        isActive:false,
        userInfo: {
            id: null,
            username: null,
            name: null,
            email: null,
            surname: null
        }
    };

    componentDidMount() {
        const userInfo = this.props.userInfo;
        if (userInfo.id == null) {
            getWhoAmI().then(userInfo => {
                this.props.currentUser(userInfo);
                getUser(userInfo.id).then( user => {
                    this.setState(prev => ({...prev, ...{userInfo: user}}));
                });
            })
        } else {
            this.setState({userInfo});
        }
    }

    render() {

        const logoutHandler = (ignore) => {
            invalidateLocalStorage();
            this.props.invalidateAuth(ignore);
        };

        const toggleMenu = (ignore) => {
            this.setState(prev => (
                {...prev, ...{isActive: !prev.isActive}})
            );
        };



        return (
            <div className="user-menu-container" onMouseEnter={toggleMenu} onMouseLeave={toggleMenu}>
                <NavLink className="expand-menu-button" to="/profile">
                    <div className="username">{this.state.userInfo.username}</div>
                    <div className="header-user-image">
                        <UserImage userInfo={this.state.userInfo}/>
                    </div>
                </NavLink>
                {this.state.isActive &&
                    <div className="user-menu">
                        <div className="menu-buttons">
                            <CreateTemplate/>
                            <CreateEvent/>
                            <div className="border-top"/>
                            <NavLink className="nav-button header-button" to="/settings">Settings</NavLink>
                            <NavLink className="nav-button header-button" to="/friends">Friends</NavLink>
                            <div className="border-top"/>
                            <NavLink className="nav-button header-button" onClick={logoutHandler} to="/">Sign-out</NavLink>
                        </div>
                    </div>
                }
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        userInfo: state.currentUser.userInfo,
    }
};

const mapDispatchToProps = {
    currentUser,
    invalidateAuth
};

export default connect(mapStateToProps, mapDispatchToProps)(UserMenu);
