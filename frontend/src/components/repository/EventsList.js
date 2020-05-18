import React, {Component, Fragment} from "react";
import {connect} from "react-redux";
import {getUser} from "../../redux/services/UserService";
import {saveUser} from "../../redux/actions/UserActions";
import {deleteTemplate, postTemplate} from "../../redux/services/TemplateService";
import {deleteEvent, putEvent} from "../../redux/services/EventService";
import {createDateTimeString} from "../../utils/FormaliseTime";

class EventsList extends Component {

    state = {};

    componentDidMount() {
        const id = this.props.userInfo.id;
        getUser(id).then(user => {
            this.props.saveUser(user);
            this.setState(user);
        });
    }

    render() {

        const deleteHandler = (event, id) => {
            event.preventDefault();
            deleteEvent(id).then(ignore => {
                const userId = this.props.userInfo.id;
                getUser(userId).then(user => {
                    this.props.saveUser(user);
                    this.setState(user);
                });
            });
        };

        const createTemplateHandler = (e, event) => {
            e.preventDefault();
            const userId = this.props.userInfo.id;
            const template = {
                ownerId: userId,
                title: event.title,
                description: event.description,
                duration: event.duration
            };
            postTemplate(template).then(created => {
                event.templateId = created.id;
                putEvent(event).then(ignore => {
                    getUser(userId).then(user => {
                        this.props.saveUser(user);
                        this.setState(user);
                    });
                });
            })
        };

        const noneTemplate = (event) => {
            return (
                <Fragment>
                    <div>none</div>
                    <button className="repository-button" onClick={e => createTemplateHandler(e, event)}>create</button>
                </Fragment>
            );
        };

        if (this.props.events.length === 0) {
            return <div className="none-message">You have no events yet</div>
        } else {
            return this.props.events.map(event =>
                <div id={event.id} className="event" key={event.id}>
                    <div className="template-header">
                        <div className="template-title">{event.title}</div>
                        <button className="close-button" style={{backgroundSize: "10px"}} onClick={e => deleteHandler(e, event.id)}/>
                    </div>
                    <div className="template-characteristics">
                        <div className="template-description">{event.description}</div>
                        <div className="template-duration">Starts at: {createDateTimeString(new Date(event.time))}</div>
                        <div className="template-duration">Event duration: {event.duration} minutes</div>
                        <div className="template-duration">Template: {event.templateId === null && noneTemplate(event)} {event.templateId}</div>
                    </div>
                </div>
            );
        }
    }
}

const mapStateToProps = state => {
    return {
        userInfo: state.currentUser.userInfo,
        events: state.user.curUser.events
    }
};

const mapDispatchToProps = {
    saveUser
};

export default connect(mapStateToProps, mapDispatchToProps)(EventsList);
