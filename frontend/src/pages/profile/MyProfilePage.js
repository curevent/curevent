import React, {Component} from "react";
import {connect} from "react-redux";
import {getWhoAmI} from "../../redux/services/AuthService";
import {currentUser} from "../../redux/actions/AuthActions";
import '../../css/profile.css';
import {Redirect} from "react-router-dom";
import {putUser} from "../../redux/services/UserService";
import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid'

class MyProfilePage extends Component {

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

        console.log(this.state);

        const submitHandler = async event => {
            event.preventDefault();
            const user = await putUser(this.state.userInfo);
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

        console.log("state" + this.state);
        return (
            <div className="profile-container">
                {!this.props.isAuth && <Redirect to="/"/>}
                <form>
                    <div className="user-info">
                        <div className="left-panel">
                            <div className="user-image">
                                {this.state.userInfo.username != null && this.state.userInfo.username.slice(0,1).toUpperCase()}
                            </div>
                            <div className="field">
                                {this.state.userInfo.username}
                            </div>
                        </div>
                        <div className="right-panel">
                            {!this.state.enableEdit && <div className="field"> {this.state.userInfo.name} </div>}
                            {this.state.enableEdit && generateInput("name")}
                            {!this.state.enableEdit && <div className="field"> {this.state.userInfo.surname} </div>}
                            {this.state.enableEdit && generateInput("surname")}
                            {!this.state.enableEdit && <div className="field"> {this.state.userInfo.country} </div>}
                            {this.state.enableEdit && generateInput("country")}
                            {!this.state.enableEdit && <div className="field"> {this.state.userInfo.city} </div>}
                            {this.state.enableEdit && generateInput("city")}
                            <div>
                                {!this.state.enableEdit && <button className="auth-button" onClick={startEditHandler}>Edit</button>}
                                {this.state.enableEdit && <button className="auth-button" onClick={submitHandler}>Save</button>}
                            </div>
                        </div>
                    </div>
                </form>
                <div className="content-container">
                    <div id="calendar" className="calendar-container">
                        <FullCalendar defaultView="dayGridMonth" plugins={[ dayGridPlugin ]} />
                    </div>
                    <div className="daily-timeline-container"></div>
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
