import React from "react";

export const DaysMenu = ({year, month, dayOfWeek}) => {

    const buildCurrentMonthArray = (year, month, dayOfWeek) => {

        const currentMonth = new Date(year, month + 1, 0);
        const monthLength = currentMonth.getDate();

        console.log(dayOfWeek);

        const days = [];

        for (let counter = 1; counter < dayOfWeek; counter++) {
            days.push("");
        }

        for (let counter = 1; counter <= monthLength; counter++) {
            days.push(counter);
        }

        return (days.map(day => <div className="day"> {day} </div>));
    };

    return (
        <div className="days">
            {buildCurrentMonthArray(year, month, dayOfWeek)}
        </div>
    );
};
