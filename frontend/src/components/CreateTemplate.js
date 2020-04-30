import React, {Component, Fragment} from "react";
import CreateTemplateWindow from "./CreateTemplateWindow";
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
                <button className="create-template-button shadow-none" onClick={this.toggleModal}>
                    <div className="create-template-icon"/>
                    <div className="create-template-text">Create template</div>
                </button>
                {this.state.isActive &&
                ReactDOM.createPortal(
                    <CreateTemplateWindow onClose={this.toggleModal}/>,
                    document.getElementById("portal")
                )}
            </Fragment>
        );
    }
}
