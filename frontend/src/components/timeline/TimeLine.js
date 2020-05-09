import React from "react";
import '../../css/timeline.css'
import {connect} from "react-redux";
import Line from "./Line";
import Calendar from "./Calendar";
import {makeTwoCharacter} from "../../utils/FormaliseTime";

function TimeLine({date, user}) {

    const start = new Date(date.start.year, date.start.month, date.start.day, date.start.hour, date.start.minute);
    const end = new Date(date.end.year, date.end.month, date.end.day, date.end.hour, date.end.minute);

    const listOfHours = () => {
        const diff = end.getTime() - start.getTime();
        const onePart = diff / 12;
        let hoursCount = [];
        for (let counter = start.getTime(); counter <= end.getTime(); counter += onePart) {
            const time = new Date(counter);
            const hour = makeTwoCharacter(time.getHours());
            const min = makeTwoCharacter(time.getMinutes());
            hoursCount.push(hour + ":" + min);
        }
        return hoursCount;
    };

    return (
        <div className="timeline-container">
            <Calendar/>
            <div className="timeline-title">You have {user.events.length} events</div>
            <div className="timeline">
                <div className="timeline-header">
                    {listOfHours().map(hour => (<div className="hour-counter">{hour}</div>))}
                </div>
                <div className="line-container">
                    <div className="line"/>
                </div>
                <div className="events">
                    {user.events.map(event => {
                        return (
                            <div
                                className="event-line"
                                style={{height: "5px", width: event.duration + "px"}}
                            >
                            </div>
                        );
                    })}
                </div>
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
