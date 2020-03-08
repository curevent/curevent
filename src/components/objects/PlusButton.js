import React, {Component, useState} from 'react';
import MouseCoordsHook from "../hooks/MouseCoordsHook";

class PlusButton extends Component {

    state = {
        visible: false,
        x: 0,
        hour: 0,
        min: 0,
    }

    componentDidMount() {
        this.state.visible = false;
        this.state.coordsX = 700;
    }

    show = () => {
        this.setVisibility(true);
    }

    hide = () => {
        this.setVisibility(false);
    }

    setVisibility = visible => {
        this.setState({visible});
    }

    updateData = (value) => {
        this.setState({ x: value })
    }

    render() {
        const {visible,x} = this.state;

        return (
            <div className="add-button-container" onMouseEnter={this.show} onMouseLeave={this.hide}>
                {visible && <div className="add-button"> {x} </div>}
                {visible && <MouseCoordsHook updateData={this.updateData}/>}
            </div>
        );
    }
}

export default PlusButton;