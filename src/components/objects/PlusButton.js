import React, {Component} from 'react';



class PlusButton extends Component {

    state = {
        visible: false,
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

    componentDidMount() {
        this.state.visible = false;
    }

    render() {
        const {visible} = this.state;

        return (
            <div className="add-button-container" onMouseEnter={this.show} onMouseLeave={this.hide}>
                {visible && <div className="add-button"> + </div>}
            </div>
        );
    }
}

export default PlusButton;