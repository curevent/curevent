import React from "react";

export const FormaliseTime = (hour,minute) => {
    minute = minute % 60;
    minute = (minute < 0) ? '00' : makeTwoCharacter(minute);
    hour = (hour < 0 || hour > 24) ? '00' : makeTwoCharacter(hour);
    return `${hour}:${minute}`
};

const makeTwoCharacter = (timeNumber) => {
    return (timeNumber < 10 && timeNumber >= 0) ? '0' + timeNumber : timeNumber;
};