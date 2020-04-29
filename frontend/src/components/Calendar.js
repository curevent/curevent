import {connect} from "react-redux";
import React from "react";
import '../css/calendar.css'
import {DaysMenu} from "./DaysMenu";

const Calendar = () => {

    var date = new Date();

    const previousMonth = (event) => {

    };

    const nextMonth = (event) => {

    };

    return (
        <div className="calendar-container">
            <div className="months-menu">
                <button onClick={previousMonth}>Previous</button>
                <button onClick={nextMonth}>Next</button>
            </div>
            <div className="days-menu-container">
                <DaysMenu year={date.getFullYear()} month={date.getMonth()}/>
            </div>
        </div>
    );
};

const mapStateToProps = state => {
    return {

    }
};

export default connect(mapStateToProps, null)(Calendar);
