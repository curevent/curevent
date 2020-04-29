import {FormaliseTime} from "../utils/FormaliseTime";
import React, {useState} from "react";
import AddMarkButton from "./AddMarkButton";
import {connect} from "react-redux";
import {useMouseMove} from "react-use-mouse-move";

const Line = ({date}) => {

    const mousePosition = useMouseMove(1);

    const startHour = date.start.hour;

    return (
        <div className="line-container">
            <div className="line"/>
        </div>
    );
};

const mapStateToProps = state => {
    return {
        date: state.timeline
    }
};

export default connect(mapStateToProps, null)(Line);
