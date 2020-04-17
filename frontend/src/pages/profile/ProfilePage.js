import React, {Component} from "react";
import {connect} from "react-redux";
import {getWhoAmI} from "../../redux/auth/AuthService";
import {currentUser, refresh} from "../../redux/auth/AuthActions";
import {Redirect} from "react-router-dom";

class ProfilePage extends Component {

    state = {
        userInfo: {
            id: null
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
        console.log("state" + this.state);
        return (
            <div className="profile-container">
                {!this.props.isAuth && <Redirect to="/"/>}
                {console.log(this.state.userInfo)}
                {this.state.userInfo.username}
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        isAuth: state.auth.isAuth,
        userInfo: state.currentUser.userInfo,
    }
};

const mapDispatchToProps = {
    currentUser,
};

export default connect(mapStateToProps, mapDispatchToProps)(ProfilePage);
