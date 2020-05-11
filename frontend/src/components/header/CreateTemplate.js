import React, {Component, Fragment} from "react";
import CreateTemplateWindow from "../repository/CreateTemplateWindow";
import ReactDOM from 'react-dom'

export class CreateTemplate extends Component {

    state = {
        isActive: false
    };

    toggleModal = () => {
        this.setState(prev => ({isActive: !prev.isActive}))
    };

    render() {
        return (
            <Fragment>
                <button
                    className="nav-button header-button"
                    onClick={this.toggleModal}
                >
                    Create template
                </button>
                {this.state.isActive &&
                ReactDOM.createPortal(
                    <CreateTemplateWindow onClose={this.toggleModal}/>,
                    document.getElementById("create-template")
                )}
            </Fragment>
        );
    }
}
