import React, {Component} from "react";
import {connect} from "react-redux";
import {getWhoAmI} from "../../redux/services/AuthService";
import {currentUser, refresh} from "../../redux/actions/AuthActions";
import {Redirect} from "react-router-dom";
import '../../css/profile.css';

class MyProfilePage extends Component {

    state = {
        userInfo: {
            id: null,
            username: null
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
                <div className="user-info">
                    <div className="user-image">
                        {this.state.userInfo.username != null && this.state.userInfo.username.slice(0,1)}
                    </div>
                    <div className="username">
                        {this.state.userInfo.username}
                    </div>
                </div>

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

export default connect(mapStateToProps, mapDispatchToProps)(MyProfilePage);
