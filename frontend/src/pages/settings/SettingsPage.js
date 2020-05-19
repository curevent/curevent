import React, {Component} from "react";
import {getWhoAmI} from "../../redux/services/AuthService";
import {putUser} from "../../redux/services/UserService";
import {currentUser} from "../../redux/actions/AuthActions";
import {connect} from "react-redux";

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
        const submitHandler = async event => {
            event.preventDefault();
            const user =
             await putUser();
            this.setState(perv => ({...perv, ...{enableEdit: false}}));
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
                    className="profile-input"
                    placeholder={`Input your ${fieldName}`}
                    value={this.state.userInfo[fieldName]}
                    onChange={changeInputHandler}
                    style={{borderRadius: "10px"}}
                />
            );
        };

        return (
            <div className="user-info">
                <div className="user-image-panel">
                    <div className="user-image">
                        {this.state.userInfo.username != null && this.state.userInfo.username.slice(0, 1).toUpperCase()}
                    </div>
                    <div className="field">
                        {this.state.userInfo.username}
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
    }
};

const mapDispatchToProps = {
    currentUser,
    putUser
};

export default connect(mapStateToProps, mapDispatchToProps)(SettingsPage);
