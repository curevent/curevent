import React, {Component} from "react";
import {connect} from "react-redux";
import {currentUser} from "../../redux/actions/AuthActions";
import '../../css/profile.css';
import {Redirect} from "react-router-dom";
import {getUser, putUser} from "../../redux/services/UserService";
import TimeLine from "../../components/timeline/TimeLine";
import UserInfo from "../../components/UserInfo";
import {getWhoAmI} from "../../redux/services/AuthService";
import {saveUser} from "../../redux/actions/UserActions";

class MyProfilePage extends Component {

    componentDidMount() {
        getWhoAmI().then(userInfo => {
                const id = userInfo.id;
                getUser(id).then(user => {
                    this.props.saveUser(user);
                    this.setState(user);
                });
            }
        );
    }

    render() {
        return (
            <div className="profile-container">
                {!this.props.isAuth && <Redirect to="/"/>}
                <form>
                    <UserInfo userInfo={this.props.page}/>
                </form>
                <div className="content-container">
                    <TimeLine user={this.props.page}/>
                </div>
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        isAuth: state.auth.isAuth,
        userInfo: state.currentUser.userInfo,
        page: state.user.curUser
    }
};

const mapDispatchToProps = {
    currentUser,
    putUser,
    saveUser
};

export default connect(mapStateToProps, mapDispatchToProps)(MyProfilePage);
