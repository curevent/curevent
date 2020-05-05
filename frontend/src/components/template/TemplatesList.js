import React, {Component} from "react";
import {connect} from "react-redux";
import {getUser} from "../../redux/actions/UserActions";
import {getTokens} from "../../utils/localStorageUtils";
import {deleteTemplateAndTemplateEvents} from "../../redux/actions/TemplateActions";

class TemplatesList extends Component {

    componentDidMount() {
        const id = this.props.userInfo.id;
        const tokens = getTokens();
        this.props.getUser(id, tokens.access)
    }

    render() {

        const deleteHandler = (event, id) => {
            event.preventDefault();
            const tokens = getTokens();
            this.props.deleteTemplateAndTemplateEvents(id, tokens.access);
            const userId = this.props.userInfo.id;
            this.props.getUser(userId, tokens.access)
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
                    <div className="template-description">{template.description}</div>
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
    getUser,
    deleteTemplateAndTemplateEvents
};

export default connect(mapStateToProps, mapDispatchToProps)(TemplatesList);
