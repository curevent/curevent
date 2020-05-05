import React, {Component} from "react";
import {putUser} from "../redux/services/UserService";
import {getWhoAmI} from "../redux/services/AuthService";
import {currentUser} from "../redux/actions/AuthActions";
import {connect} from "react-redux";
import {CountryAndCity} from "./fields/CountryAndCity";
import {NameAndUsername} from "./fields/NameAndUsername";
import {UserImage} from "./fields/UserImage";

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
                    <UserImage userInfo={this.state.userInfo}/>
                </div>
                <NameAndUsername userInfo={this.state.userInfo}/>
                <div className="field contact-info"> {this.state.userInfo.email} </div>
                <CountryAndCity userInfo={this.state.userInfo}/>
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
