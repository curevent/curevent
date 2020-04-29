import {connect} from "react-redux";
import React, {Fragment} from "react";

export const DaysMenu = ({year, month}) => {

    const buildCurrentMonthArray = (year, month) => {

        const monthLength = new Date(year, month, 0).getDate();

        const days = [];

        for (let counter = 1; counter <= monthLength; counter++) {
            days.push(counter);
        }

        return (days.map(day => <div className="day" style={{background: getColor(day)}}> {day} </div>));
    };

    const getColor = (day) => {
        if((day % 2) === 1) {
            return "#cdcdcd"
        } else {
            return "#e7e7e7"
        }
    };

    return (
        <div className="days">
            {buildCurrentMonthArray(year, month)}
        </div>
    );
};
