import React, {Component} from "react";
import {connect} from "react-redux";
import '../../css/templates.css'
import {
    categoriesRepository,
    eventsRepository,
    expand,
    minimize,
    tagsRepository,
    templatesRepository
} from "../../redux/actions/repositoryActions";
import ExpandPanel from "./ExpandPanel";
import TemplatesList from "./TemplatesList";
import EventsList from "./EventsList";
import CategoriesList from "./CategoriesList";
import TagsList from "./TagsList";

class Repository extends Component {

    render() {

        return (
            <div className="templates-board">
                <ExpandPanel/>
                {!this.props.isMinimized &&
                    <div className="repository-panel">
                        {this.props.contentType === "templates" && <TemplatesList/>}
                        {this.props.contentType === "events" && <EventsList/>}
                        {this.props.contentType === "categories" && <CategoriesList/>}
                        {this.props.contentType === "tags" && <TagsList/>}
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
        isMinimized: state.repo.isMinimized,
        contentType: state.repo.content
    }
};

const mapDispatchToProps = {
    expand,
    minimize
};

export default connect(mapStateToProps, mapDispatchToProps)(Repository);
