import {connect} from "react-redux";
import React, {useState} from "react";
import '../../css/calendar.css'
import {DaysMenu} from "./DaysMenu";
import DateBoard from "./DateBoard";

const monthNames = ["January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
];

const Calendar = () => {

    const today = new Date();

    const [date, setDate] = useState(new Date(today.getFullYear(), today.getMonth(), 1));


    const previousMonth = (event) => {
        if (date.getMonth() > 0) {
            setDate(new Date(date.getFullYear(),date.getMonth() - 1, 1))
        } else {
            setDate(new Date(date.getFullYear() - 1, 11, 1));
        }
    };

    const nextMonth = (event) => {
        if (date.getMonth() < 11) {
            setDate(new Date(date.getFullYear(),date.getMonth() + 1, 1))
        } else {
            setDate(new Date(date.getFullYear() + 1, 0, 1));
        }
    };

    return (
        <div className="calendar-container">
            <div className="left-menu">
                <div className="months-menu">
                    <button className="calendar-button previous" onClick={previousMonth}/>
                    {monthNames[date.getMonth()]} {date.getFullYear()}
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
