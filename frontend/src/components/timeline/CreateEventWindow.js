import React, {Fragment, useState} from "react";
import {connect} from "react-redux";
import {saveUser} from "../../redux/actions/UserActions";
import {getUser} from "../../redux/services/UserService";
import {postEvent} from "../../redux/services/EventService";
import {CreateNewEvent} from "./CreateNewEvent";
import {CreateEventsByTemplate} from "./CreateEventsByTemplate";
import {createTemplateEvents} from "../../redux/services/TemplateService";

const CreateEventWindow = ({onClose, template, userInfo, saveUser}) => {

    const [event, setEvent] = useState({date: new Date()});

    const [templateEvent, setTemplateEvent] = useState({
        ...template,
        ...{
            date: new Date(),
            endDate: new Date(),
            repeatType: "NONE",
            repeatInterval: 1,
        }
    });

    const createEventHandler = (ignore) => {
        const newEvent = event;
        newEvent.ownerId = userInfo.id;
        newEvent.time = event.date;
        postEvent(newEvent).then(ignore => {
            getUser(userInfo.id).then(user => saveUser(user));
        });
        onClose();
    };
    const handleLocationChange = position  => {
        console.log(position)
        setEvent(prevState => ({...prevState, ...{longitude: position.position.lng, latitude: position.position.lat}}));
  }

    const eventsByTemplateHandler = (ignore) => {
        const repeatForm = {
            startTime: templateEvent.date,
            endTime: templateEvent.endDate,
            repeatType: templateEvent.repeatType,
            repeatInterval: templateEvent.repeatInterval
        };
        createTemplateEvents(repeatForm, templateEvent.id).then(templateResp => {
            getUser(userInfo.id).then(user => saveUser(user));
        });
        onClose();
    };

    const changeEventHandler = event => {
        event.persist();
        setEvent(prevState => ({...prevState, ...{[event.target.id]: event.target.value}}));
    };

    const changeTemplateHandler = event => {
        event.persist();
        setTemplateEvent(prevState => ({...prevState, ...{[event.target.id]: event.target.value}}));
    };

    const selectHandler = event => {
        event.persist();
        console.log(event.target.value);
        setTemplateEvent(prevState => ({...prevState, ...{repeatType: event.target.value}}));
    };

    const changeEventDateHandler = (date) => {
        setEvent(prevState => ({...prevState, ...{date: date}}));
    };

    const changeTemplateDateHandler = (date, type) => {
        if (type === "end") {
            setTemplateEvent(prevState => ({...prevState, ...{endDate: date}}));
        } else {
            setTemplateEvent(prevState => ({...prevState, ...{date: date}}));
        }
    };

    console.log(templateEvent);
    if (template === undefined) {
        return <CreateNewEvent
            event={event}
            onClose={onClose}
            changeStateHandler={changeEventHandler}
            submitHandler={createEventHandler}
            changeDateHandler={changeEventDateHandler}
            handleLocationChange={handleLocationChange}
        />
    } else {
        return <CreateEventsByTemplate
            template={templateEvent}
            submitHandler={eventsByTemplateHandler}
            changeStateHandler={changeTemplateHandler}
            changeDateHandler={changeTemplateDateHandler}
            onClose={onClose}
            selectHandler={selectHandler}
        />
    }
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
