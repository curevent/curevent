import React, {Component} from "react";
import {connect} from "react-redux";
import '../../css/templates.css'
import {expand, minimize} from "../../redux/actions/TemplateActions";
import ExpandPanel from "./ExpandPanel";
import TemplatesList from "./TemplatesList";

class TemplatesRepository extends Component {
    render() {

        const expandHandler = (event) => {
            if (this.props.isMinimized) {
                this.props.expand()
            } else {
                this.props.minimize()
            }
        };

        return (
            <div className="templates-board">
                <ExpandPanel/>
                {!this.props.isMinimized &&
                    <div className="repository-panel">
                        <TemplatesList/>
                    </div>
                }
                {this.props.isMinimized && <div className="empty-space"/>}
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        templates: state.templates,
        isMinimized: state.repo.isMinimized
    }
};

const mapDispatchToProps = {
    expand,
    minimize
};

export default connect(mapStateToProps, mapDispatchToProps)(TemplatesRepository);
