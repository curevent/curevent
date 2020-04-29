import {makeTwoCharacter} from "../utils/FormaliseTime";
import React from "react";
import {connect} from "react-redux";

const DateBoard = ({date}) => {
    return (
        <div className="selected-date">
            <div className="start-date">
                {makeTwoCharacter(date.start.day)}.
                {makeTwoCharacter(date.start.month)}.
                {date.start.year}
            </div>
            <div className="split">/</div>
            <div className="end-date">
                {makeTwoCharacter(date.end.day)}.
                {makeTwoCharacter(date.end.month)}.
                {date.end.year}
            </div>
        </div>
    );
};

const mapStateToProps = state => {
    return {
        date: state.timeline
    }
};

export default connect(mapStateToProps, null)(DateBoard);
