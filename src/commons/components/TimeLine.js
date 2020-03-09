import React, {Component, useRef, useState} from 'react';
import '../css/timeline.css'
import {useMouseMove} from "react-use-mouse-move";
import {FormaliseTime} from "../utils/FormaliseTime";
import Line from "./Line";

export default function TimeLine() {

    const [state, setState] = useState({
        startTimeLimit: {day: 21, hour: 12, minute: 0},
        endTimeLimit: {day: 21, hour: 14, minute: 0},
        now: {day: 21, hour: 13, minute: 0},
    });

    const [period, setPeriod] = useState(
        1
    );

    const mousePosition = useMouseMove(1);

    const startTimeLimit = state.startTimeLimit;

    const endTimeLimit = state.endTimeLimit;

    const now = FormaliseTime(state.now.hour, state.now.minute);

    const startTime = FormaliseTime(startTimeLimit.hour, startTimeLimit.minute);

    const endTime = FormaliseTime(endTimeLimit.hour, endTimeLimit.minute);

    return (
        <div className="timeline-container">
            <div className="time-limit">
                { startTime }
            </div>
            <Line mousePosition={mousePosition} period={period} startHour={startTimeLimit.hour} id={"left"}/>
            <div className="now-container">
                <div className="now-title">Now</div>
                <div className="now-time">{now}</div>
            </div>
            <Line mousePosition={mousePosition} period={period} startHour={state.now.hour} id={"right"}/>
            <div className="time-limit">
                { endTime }
            </div>
        </div>
    );
}