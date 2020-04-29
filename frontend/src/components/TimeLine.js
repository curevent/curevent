import React, {useState} from "react";
import {useMouseMove} from "react-use-mouse-move";
import '../css/timeline.css'
import {connect} from "react-redux";
import DateBoard from "./DateBoard";
import Line from "./Line";

function TimeLine({date}) {

    return (
        <div className="timeline-container">
            <DateBoard/>
            <div className="timeline">
                <Line/>
            </div>
        </div>
    );
}

const mapStateToProps = state => {
    return {
        date: state.timeline
    }
};

export default connect(mapStateToProps, null)(TimeLine);
