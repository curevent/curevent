import React, {Component} from "react";
import {connect} from "react-redux";
import {getWhoAmI} from "../../redux/services/AuthService";
import {currentUser} from "../../redux/actions/AuthActions";
import '../../css/profile.css';
import {Redirect} from "react-router-dom";
import {putUser} from "../../redux/services/UserService";
import Calendar from "../../components/Calendar";
import TimeLine from "../../components/TimeLine";
import UserInfo from "../../components/UserInfo";

class MyProfilePage extends Component {

    render() {
        return (
            <div className="profile-container">
                {!this.props.isAuth && <Redirect to="/"/>}
                <form>
                    <UserInfo/>
                </form>
                <div className="content-container">
                    <Calendar/>
                    <TimeLine/>
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
    putUser
};

export default connect(mapStateToProps, mapDispatchToProps)(MyProfilePage);
