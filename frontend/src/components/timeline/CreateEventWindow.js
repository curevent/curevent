import React, {Fragment, useState} from "react";
import {connect} from "react-redux";
import {saveUser} from "../../redux/actions/UserActions";
import {getUser} from "../../redux/services/UserService";
import {postEvent} from "../../redux/services/EventService";

const CreateEventWindow = ({onClose, userInfo, saveUser}) => {

    const [event, setEvent] = useState({});

    const submitHandler = (event) => {
        const newEvent = event;
        newEvent.ownerId = userInfo.id;
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
                        <div className="template-layout">Title:</div>
                        <input
                            id="title"
                            value={event.title}
                            className="template-input"
                            onChange={changeStateHandler}
                        />
                    </div>
                    <div className="field">
                        <div className="template-layout">Description:</div>
                        <textarea
                            id="description"
                            value={event.description}
                            className="template-input template-textarea"
                            onChange={changeStateHandler}
                        />
                    </div>
                    <div className="field">
                        <div className="template-layout">Duration:</div>
                        <input
                            id="duration"
                            type="number"
                            placeholder="Time your event in minutes"
                            value={event.duration}
                            className="template-input"
                            onChange={changeStateHandler}
                        />
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
