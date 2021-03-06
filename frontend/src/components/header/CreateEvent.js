import React, {Component, Fragment} from "react";
import ReactDOM from "react-dom";
import CreateEventWindow from "../timeline/CreateEventWindow";

export class CreateEvent extends Component {

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
                    Create event
                </button>
                {this.state.isActive &&
                ReactDOM.createPortal(
                    <CreateEventWindow onClose={this.toggleModal}/>,
                    document.getElementById("create-event")
                )}
            </Fragment>
        );
    }
}
