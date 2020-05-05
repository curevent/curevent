import {connect} from "react-redux";
import React from "react";
import '../../css/calendar.css'
import {DaysMenu} from "./DaysMenu";
import DateBoard from "./DateBoard";

const monthNames = ["January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
];

const Calendar = () => {

    let date = new Date();

    const previousMonth = (event) => {

    };

    const nextMonth = (event) => {

    };

    return (
        <div className="calendar-container">
            <div className="left-menu">
                <div className="months-menu">
                    <button className="calendar-button previous" onClick={previousMonth}/>
                    {monthNames[date.getMonth()]}
                    <button className="calendar-button next" onClick={nextMonth}/>
                </div>
                <div className="days-menu-container">
                    <DaysMenu year={date.getFullYear()} month={date.getMonth()} dayOfWeek={date.getDay()}/>
                </div>
            </div>
            <DateBoard/>
            <div className="control-menu"/>
        </div>
    );
};

const mapStateToProps = state => {
    return {

    }
};

export default connect(mapStateToProps, null)(Calendar);
