import React from "react";

const AddMarkButton = (props) => {

    const mousePosition = props.mousePosition.x;

    return (
        <div className="add-button-container" style={{left:mousePosition + "px"}}>
            <div className="add-button-title"> Add mark </div>
            <div className="add-button-slider"> { props.time } </div>
        </div>

    );
};

export default AddMarkButton;
