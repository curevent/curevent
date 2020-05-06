import React, {Fragment, useState} from "react";
import {connect} from "react-redux";
import {saveUser} from "../../redux/actions/UserActions";
import {getUser} from "../../redux/services/UserService";
import {postEvent} from "../../redux/services/EventService";

const CreateEventWindow = ({onClose, userInfo, saveUser}) => {

    const [event, setEvent] = useState({date:new Date()});

    const submitHandler = (ignore) => {
        const newEvent = event;
        newEvent.ownerId = userInfo.id;
        newEvent.time = new Date().getTime();
        postEvent(newEvent).then(ignore => {
            getUser(userInfo.id).then(user => saveUser(user));
        });
        onClose();
    };

    const changeStateHandler = event => {
        event.persist();
        setEvent(prevState => ({...prevState, ...{[event.target.id]: event.target.value}}));
    };

    return (
        <Fragment>
            <div className="create-template-window">
                <div className="create-template-container">
                    <div className="ct-title">
                        <div className="ct-title-message">Create event</div>
                        <button className="close-button" onClick={onClose}/>
                    </div>
                    <div className="field">
                        <div className="template-layout">title:</div>
                        <input
                            id="title"
                            value={event.title}
                            className="template-input"
                            onChange={changeStateHandler}
                            autoComplete="off"
                        />
                    </div>
                    <div className="field">
                        <div className="template-layout">description:</div>
                        <textarea
                            id="description"
                            value={event.description}
                            className="template-input template-textarea"
                            onChange={changeStateHandler}
                            autoComplete="off"
                        />
                    </div>
                    <div className="field">
                        <div className="template-layout">duration:</div>
                        <input
                            id="duration"
                            type="number"
                            placeholder="Time your event in minutes"
                            value={event.duration}
                            className="template-input"
                            onChange={changeStateHandler}
                            autoComplete="off"
                        />
                    </div>
                    <div className="field">
                        <div className="template-layout">date:</div>
                        <div className="date-input">
                            <input placeholder="12"/>.<input placeholder="04"/>.<input placeholder="2020"/>
                        </div>
                        <div className="template-layout">time:</div>
                        <div className="date-input">
                            <input placeholder="12"/>:<input placeholder="00"/>
                        </div>
                    </div>
                    <div className="template-window-buttons">
                        <button className="auth-button" onClick={submitHandler}>Create</button>
                    </div>
                </div>
            </div>
        </Fragment>
    );
};

const mapStateToProps = state => {
    return {
        userInfo: state.currentUser.userInfo,
    }
};

const mapDispatchToProps = {
    saveUser
};

export default connect(mapStateToProps, mapDispatchToProps)(CreateEventWindow);
