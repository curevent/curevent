import {makeTwoCharacter} from "../../utils/FormaliseTime";
import React from "react";
import {connect} from "react-redux";

const DateBoard = ({date}) => {
    return (
        <div className="selected-date">
            <div className="date-time">
                <div className="title">Start time</div>
                <input type="time" className="time" defaultValue={
                    makeTwoCharacter(date.start.hour) +
                    ":" + makeTwoCharacter(date.start.minute)}/>
                <div className="date">
                    {makeTwoCharacter(date.start.day)}.
                    {makeTwoCharacter(date.start.month)}.
                    {date.start.year}
                </div>
            </div>
            <div className="date-time">
                <div className="title">End time</div>
                <input type="time" className="time" defaultValue={
                    makeTwoCharacter(date.end.hour) +
                    ":" + makeTwoCharacter(date.end.minute)}/>
                <div className="date">
                    {makeTwoCharacter(date.end.day)}.
                    {makeTwoCharacter(date.end.month)}.
                    {date.end.year}
                </div>
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
