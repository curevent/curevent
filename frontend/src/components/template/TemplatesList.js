import React, {Component} from "react";
import {connect} from "react-redux";

import {deleteTemplateAndTemplateEvents} from "../../redux/actions/TemplateActions";
import {getUser} from "../../redux/services/UserService";
import {saveUser} from "../../redux/actions/UserActions";

class TemplatesList extends Component {

    state = {templates:[]};

    componentDidMount() {
        const id = this.props.userInfo.id;
        getUser(id).then(user => this.props.saveUser(user));
        this.setState({templates: this.props.templates});
    }

    render() {

        const deleteHandler = (event, id) => {
            event.preventDefault();
            deleteTemplateAndTemplateEvents(id);
            const userId = this.props.userInfo.id;
            getUser(userId).then(user => this.props.saveUser(user));
            this.setState([]);
        };

        if (this.props.templates.length === 0) {
            return <div className="none-templates">You have no templates yet</div>
        } else {
            return this.props.templates.map(template =>
                <div id={template.id} className="template" key={template.id}>
                    <div className="template-header">
                        <div className="template-title">{template.title}</div>
                        <button className="close-button" style={{backgroundSize: "10px"}} onClick={event => deleteHandler(event, template.id)}/>
                    </div>
                    <div className="template-characteristics">
                        <div className="template-description">Description: {template.description}</div>
                        <div className="template-duration">Duration: {template.duration} minutes</div>
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
    saveUser,
    deleteTemplateAndTemplateEvents
};

export default connect(mapStateToProps, mapDispatchToProps)(TemplatesList);
