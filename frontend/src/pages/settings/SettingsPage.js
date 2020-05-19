import React, {Component} from "react";
import {getWhoAmI} from "../../redux/services/AuthService";
import {getUser, putUser} from "../../redux/services/UserService";
import {currentUser} from "../../redux/actions/AuthActions";
import {connect} from "react-redux";
import {UserImage} from "../../components/fields/UserImage";
import {saveUser} from "../../redux/actions/UserActions";

class SettingsPage extends Component {

    state = {
        enableEdit: false,
        userInfo: {
            id: null,
            username: null,
            name: null,
            surname: null
        }
    };

    componentDidMount() {
        getWhoAmI().then(userInfo => {
                const id = userInfo.id;
                getUser(id).then(user => {
                    this.props.saveUser(user);
                    this.setState({userInfo: userInfo});
                });
            }
        );
    }

    render() {
        const submitHandler = event => {
            event.preventDefault();
            const user = this.state.userInfo;
            putUser(user).then( ignore => {
                this.setState(perv => ({...perv, ...{enableEdit: false}}));
            });
        };

        const startEditHandler = () => {
            this.setState(perv => ({...perv, ...{enableEdit: true}}));
        };

        const changeInputHandler = event => {
            event.persist();
            this.setState(pervState => ({
                ...pervState,
                userInfo: {
                    ...pervState.userInfo,
                    ...{[event.target.id]: event.target.value}
                }
            }));
        };

        const generateInput = (fieldName) => {
            return (
                <input
                    type="text"
                    id={fieldName}
                    className="auth-input"
                    placeholder={`Input your ${fieldName}`}
                    value={this.state.userInfo[fieldName]}
                    onChange={changeInputHandler}
                    style={{borderRadius: "10px"}}
                />
            );
        };

        return (
            <div className="profile-container">
                <div className="user-info">
                    <div className="user-image-panel">
                        <UserImage userInfo={this.props.page}/>
                    </div>
                </div>
                <div className="main-info-panel">
                    {!this.state.enableEdit && <div className="field left-text"> {this.state.userInfo.name} </div>}
                    {this.state.enableEdit && generateInput("name")}
                    {!this.state.enableEdit && <div className="field left-text"> {this.state.userInfo.surname} </div>}
                    {this.state.enableEdit && generateInput("surname")}
                    {!this.state.enableEdit && <div className="field left-text"> {this.state.userInfo.country} </div>}
                    {this.state.enableEdit && generateInput("country")}
                    {!this.state.enableEdit && <div className="field left-text"> {this.state.userInfo.city} </div>}
                    {this.state.enableEdit && generateInput("city")}
                    <div>
                        {!this.state.enableEdit &&
                        <button className="auth-button" onClick={startEditHandler}>Edit</button>}
                        {this.state.enableEdit && <button className="auth-button" onClick={submitHandler}>Save</button>}
                    </div>
                </div>
            </div>

        );
    }
}

const mapStateToProps = state => {
    return {
        userInfo: state.currentUser.userInfo,
        page: state.user.curUser,
    }
};

const mapDispatchToProps = {
    currentUser,
    putUser,
    saveUser
};

export default connect(mapStateToProps, mapDispatchToProps)(SettingsPage);
