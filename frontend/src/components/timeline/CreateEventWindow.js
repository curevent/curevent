import React, {Fragment, useState} from "react";
import {connect} from "react-redux";
import {saveUser} from "../../redux/actions/UserActions";
import {getUser} from "../../redux/services/UserService";
import {postEvent} from "../../redux/services/EventService";
import {CreateNewEvent} from "./CreateNewEvent";

const CreateEventWindow = ({onClose, template, userInfo, saveUser}) => {

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

    return <CreateNewEvent
        event={event}
        onClose={onClose}
        changeStateHandler={changeStateHandler}
        submitHandler={submitHandler}
    />
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
