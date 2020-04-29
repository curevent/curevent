export const FormaliseTime = (hour,minute) => {
    minute = minute % 60;
    minute = (minute < 0) ? '00' : makeTwoCharacter(minute);
    hour = (hour < 0 || hour > 24) ? '00' : makeTwoCharacter(hour);
    return `${hour}:${minute}`
};

export const makeTwoCharacter = (timeNumber) => {
    return (timeNumber < 10 && timeNumber >= 0) ? '0' + timeNumber : timeNumber;
};

export const countTime = (element, period, mousePosition, startHour) => {

    const x = mousePosition.x;
    const timeLineLength = element.offsetWidth;
    const timeLineStart = element.offsetLeft;
    const hourCoefficient = timeLineLength/period;
    const minuteCoefficient = hourCoefficient/60;
    const hour = startHour + Math.floor((x - timeLineStart) / hourCoefficient);
    const minute = Math.floor((x - timeLineStart) / minuteCoefficient);

    return FormaliseTime(hour, minute);
};
