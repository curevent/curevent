import React from "react";
import '../../css/timeline.css'
import {connect} from "react-redux";
import Line from "./Line";
import Calendar from "./Calendar";

function TimeLine({date}) {

    return (
        <div className="timeline-container">
            <Calendar/>
            <div className="title timeline-title">Timeline</div>
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
