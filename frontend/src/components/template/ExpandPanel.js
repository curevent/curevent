import {expand, minimize} from "../../redux/actions/TemplateActions";
import {connect} from "react-redux";
import React, {Component} from "react";

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
    minimize
};

export default connect(mapStateToProps, mapDispatchToProps)(ExpandPanel);
