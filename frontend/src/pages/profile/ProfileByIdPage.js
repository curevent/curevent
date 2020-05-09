import React, {Component, useState} from "react";
import {connect} from "react-redux";
import {Redirect} from "react-router-dom";
import UserInfo from "../../components/UserInfo";
import {saveUser} from "../../redux/actions/UserActions";
import {getUser} from "../../redux/services/UserService";

class ProfileByIdPage extends Component {

    componentDidMount() {
        console.log(this.props.match.params);
        const {id} = this.props.match.params;
        getUser(id).then(user => {
            this.props.saveUser(user);
            this.setState(user);
        });
    }

    render() {
        return (
            <div className="profile-container">
                <UserInfo userInfo={this.props.page}/>
            </div>
        );
    }
};

const mapStateToProps = state => {
    return {
        isAuth: state.auth.isAuth,
        page: state.user.curUser
    }
};

const mapDispatchToProps = {
    saveUser
};

export default connect(mapStateToProps, mapDispatchToProps)(ProfileByIdPage);
