import React, {Component} from "react";
import {connect} from "react-redux";

class TemplatesList extends Component {
    render() {

        if (this.props.templates.length === 0) {
            return <div className="none-templates">You have not templates yet</div>
        } else {
            return this.props.templates.map(template =>
                <div className="template" key={template.id}>
                    <div className="template-title">{template.title}</div>
                    <div className="template-description">{template.description}</div>
                </div>
            );
        }
    }
}

const mapStateToProps = state => {
    return {
        templates: state.templates.templates
    }
};



export default connect(mapStateToProps, null)(TemplatesList);
