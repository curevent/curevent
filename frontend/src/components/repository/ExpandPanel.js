import {
    categoriesRepository,
    eventsRepository,
    expand,
    minimize,
    tagsRepository,
    templatesRepository
} from "../../redux/actions/repositoryActions";
import {connect} from "react-redux";
import React, {Component, Fragment} from "react";

class ExpandPanel extends Component {
    render() {

        const expandHandler = (event) => {
            if (this.props.isMinimized) {
                this.props.expand()
            } else {
                this.props.minimize()
            }
        };

        return (
            <div className="minimized-panel">
                <button className="expand-button" onClick={expandHandler}/>
                {!this.props.isMinimized &&
                        <Fragment>
                            <button className="nav-button header-button" onClick={this.props.tagsRepository}>tags</button>
                            <button className="nav-button header-button" onClick={this.props.templatesRepository}>templates</button>
                            <button className="nav-button header-button" onClick={this.props.eventsRepository}>events</button>
                            <button className="nav-button header-button" onClick={this.props.categoriesRepository}>categories</button>
                        </Fragment>
                }
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        isMinimized: state.repo.isMinimized
    }
};

const mapDispatchToProps = {
    expand,
    minimize,
    templatesRepository,
    tagsRepository,
    eventsRepository,
    categoriesRepository
};

export default connect(mapStateToProps, mapDispatchToProps)(ExpandPanel);
