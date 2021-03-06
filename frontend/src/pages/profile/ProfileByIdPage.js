import React, {Component} from "react";
import {connect} from "react-redux";
import UserInfo from "../../components/UserInfo";
import {saveUser} from "../../redux/actions/UserActions";
import {getUser} from "../../redux/services/UserService";
import TimelineCurevent from "../../components/timeline/TimeLine";

class ProfileByIdPage extends Component {

    updateState() {
        const {id} = this.props.match.params;
        getUser(id).then(user => {
            this.props.saveUser(user);
            this.setState(user);
        });
    }

    render() {

        const {id} = this.props.match.params;

        if (this.state == null || this.state.id !== id) {
            this.updateState();
        }

        return (
            <div className="profile-container">
                <UserInfo userInfo={this.props.page}/>
                <div className="content-container">
                    <TimelineCurevent user={this.props.page} listEvent={this.props.page.events}/>
                </div>
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
