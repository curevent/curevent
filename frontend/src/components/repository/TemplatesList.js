import React, {Component} from "react";
import {connect} from "react-redux";
import {getUser} from "../../redux/services/UserService";
import {saveUser} from "../../redux/actions/UserActions";
import {deleteTemplate} from "../../redux/services/TemplateService";

class TemplatesList extends Component {

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
            deleteTemplate(id).then(ignore => {
                const userId = this.props.userInfo.id;
                getUser(userId).then(user => {
                    this.props.saveUser(user);
                    this.setState(user);
                });
            });
        };

        const toEventHandler = (event, id) => {
            event.preventDefault();

        };

        if (this.props.templates.length === 0) {
            return <div className="none-message">You have no templates yet</div>
        } else {
            return this.props.templates.map(template =>
                <div id={template.id} className="template" key={template.id}>
                    <div className="template-header">
                        <div className="template-title">{template.title}</div>
                        <button className="to-event-button" style={{backgroundSize: "10px"}} onClick={event => toEventHandler(event, template.id)}/>
                        <button className="close-button" style={{backgroundSize: "10px"}} onClick={event => deleteHandler(event, template.id)}/>
                    </div>
                    <div className="template-characteristics">
                        <div className="template-description">{template.description}</div>
                        <div className="template-duration">Event duration: {template.duration} minutes</div>
                    </div>
                </div>
            );
        }
    }
}

const mapStateToProps = state => {
    return {
        userInfo: state.currentUser.userInfo,
        templates: state.user.curUser.templates
    }
};

const mapDispatchToProps = {
    saveUser
};

export default connect(mapStateToProps, mapDispatchToProps)(TemplatesList);
