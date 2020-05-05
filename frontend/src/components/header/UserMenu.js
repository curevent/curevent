import React, {Component} from "react";
import {getWhoAmI} from "../../redux/services/AuthService";
import {currentUser, invalidateAuth} from "../../redux/actions/AuthActions";
import {connect, useDispatch} from "react-redux";
import {UserImage} from "../fields/UserImage";
import {CreateTemplate} from "./CreateTemplate";
import {invalidateLocalStorage} from "../../utils/localStorageUtils";

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
                this.setState({userInfo});
            })
        } else {
            this.setState({userInfo});
        }
    }

    render() {

        const AUTH_PAGE = "http://localhost:3000/";

        const logoutHandler = (ignore) => {
            invalidateLocalStorage();
            this.props.invalidateAuth(ignore);
            window.location.replace(AUTH_PAGE);
        };

        const toggleMenu = (ignore) => {
            this.setState(prev => (
                {...prev, ...{isActive: !prev.isActive}})
            );
        };

        return (
            <div className="user-menu-container" onMouseEnter={toggleMenu} onMouseLeave={toggleMenu}>
                <div className="expand-menu-button">
                    <div className="username">{this.state.userInfo.username}</div>
                    <div className="header-user-image">
                        <UserImage userInfo={this.state.userInfo}/>
                    </div>
                </div>
                {this.state.isActive &&
                    <div className="user-menu">
                        <div className="menu-buttons">
                            <CreateTemplate/>
                            <div className="border-top"/>
                            <button className="nav-button header-button" onClick={logoutHandler}>Sign-out</button>
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
