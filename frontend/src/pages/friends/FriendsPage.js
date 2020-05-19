import React, {Component} from "react";
import {getWhoAmI} from "../../redux/services/AuthService";
import {getUser, getUserFriends, putUser} from "../../redux/services/UserService";
import {currentUser} from "../../redux/actions/AuthActions";
import {connect} from "react-redux";
import {saveUser} from "../../redux/actions/UserActions";

class FriendsPage extends Component {

    state = {};

    componentWillMount() {
        getWhoAmI().then(userInfo => {
                const id = userInfo.id;
                getUser(id).then(user => {
                    getUserFriends(id).then(friends => {
                        this.props.saveUser(user);
                        this.setState({friends: friends});
                    });
                });
            }
        );
    }

    render() {

        console.log(this.state);

        const renderFriends = () => {
            const friends = this.state.friends;
            if (friends === null || friends === undefined || friends.length === 0) {
                return "You have no friends. Sorry :((("
            } else {
                return allFriends();
            }
        };

        const allFriends = () => {
            return this.state.friends.map(friend => {
                return (
                    <div id={friend.id} key={friend.id}>
                        {friend.username}
                    </div>
                );
            });
        };


        return (
            <div className="friends-container">
                {renderFriends()}
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

export default connect(mapStateToProps, mapDispatchToProps)(FriendsPage);
