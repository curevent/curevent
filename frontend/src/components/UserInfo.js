import React, {Component} from "react";
import {putUser} from "../redux/services/UserService";
import {getWhoAmI} from "../redux/services/AuthService";
import {currentUser} from "../redux/actions/AuthActions";
import {connect} from "react-redux";

class UserInfo extends Component {

    state = {
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
        return (
            <div className="user-info">
                <div className="user-image-panel">
                    <div className="user-image">
                        {this.state.userInfo.username != null && this.state.userInfo.username.slice(0, 1).toUpperCase()}
                    </div>
                </div>
                <div className="field name"> {this.state.userInfo.name} {this.state.userInfo.surname} </div>
                <div className="field login"> {this.state.userInfo.username} </div>
                <div className="field contact-info"> {this.state.userInfo.email} </div>
                <div className="field contact-info"> {this.state.userInfo.country}, {this.state.userInfo.city} </div>
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
    putUser
};

export default connect(mapStateToProps, mapDispatchToProps)(UserInfo);
