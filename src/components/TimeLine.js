import React, {Component} from 'react';
import '../css/components/TimeLine.css'
import PlusButton from "./objects/PlusButton";

class TimeLine extends Component {

    render() {

        const times = ['12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00', '23:00', '00:00'];

        return (
            <div className="timeline-container">
                <div className="time-container">
                    {
                        times.map(time =>
                            <div className="time">
                                {time}
                                <div className="time-mark"/>
                            </div>
                        )
                    }
                </div>
                <div className="line-container">
                    <PlusButton/>
                    <div className="line"/>
                </div>
                <div className="now">Now</div>
            </div>
        );
    }
}

export default TimeLine;