import React, {Component} from "react";
import {connect} from "react-redux";
import {getUser} from "../../redux/services/UserService";
import {saveUser} from "../../redux/actions/UserActions";
import {deleteTemplate} from "../../redux/services/TemplateService";
import {deleteEvent} from "../../redux/services/EventService";

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
                        <div className="template-duration">Event duration: {event.duration} minutes</div>
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
