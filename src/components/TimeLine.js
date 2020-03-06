import React from 'react';

function TimeLine() {
    return (
        <div className="timeline-container">
            <div className="time">
                <div className="time-item">12:00</div>
                <div className="time-item">00:00</div>
            </div>
            <div className="line-container">
                <div className="line"></div>
                <i/>
            </div>
        </div>
    );
}

export default TimeLine;