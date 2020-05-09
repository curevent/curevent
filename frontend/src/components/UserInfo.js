import React, {Component} from "react";
import {getUser, putUser} from "../redux/services/UserService";
import {getWhoAmI} from "../redux/services/AuthService";
import {currentUser} from "../redux/actions/AuthActions";
import {connect} from "react-redux";
import {CountryAndCity} from "./fields/CountryAndCity";
import {NameAndUsername} from "./fields/NameAndUsername";
import {UserImage} from "./fields/UserImage";
import {saveUser} from "../redux/actions/UserActions";

class UserInfo extends Component {

    componentDidMount() {
        const id = this.props.userInfo.id;
        getUser(id).then(user => {
            this.props.saveUser(user);
            this.setState(user);
        });
    }

    render() {
        return (
            <div className="user-info">
                <div className="user-image-panel">
                    <UserImage userInfo={this.props.userInfo}/>
                </div>
                <NameAndUsername userInfo={this.props.userInfo}/>
                <div className="field contact-info"> {this.props.userInfo.email} </div>
                <CountryAndCity userInfo={this.props.userInfo}/>
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
    }
};

const mapDispatchToProps = {
    currentUser,
    putUser,
    saveUser
};

export default connect(mapStateToProps, mapDispatchToProps)(UserInfo);
