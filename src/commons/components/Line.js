import AddMarkButton from "./AddMarkButton";
import React, {useState} from "react";
import {FormaliseTime} from "../utils/FormaliseTime";

export default (props) => {

    console.log(props.startHour);

    const mousePosition = props.mousePosition;
    const period = props.period;
    const startHour = props.startHour;
    const id = props.id;

    const [sliderVisible, setSliderVisible] = useState(
        false
    );

    return (
        <div className={`line-container ${id}-border`} id={`${id}-line`}
             onMouseEnter={event => setSliderVisible(true)}
             onMouseLeave={event => setSliderVisible(false)}>
            {sliderVisible && <AddMarkButton mousePosition={mousePosition} time={countTime(document.getElementById(`${id}-line`),period,mousePosition, startHour)}/>}
            <div className="line"/>
        </div>
    );
};

const countTime = (element, period, mousePosition, startHour) => {

    const x = mousePosition.x;
    const timeLineLength = element.offsetWidth;
    const timeLineStart = element.offsetLeft;
    const hourCoefficient = timeLineLength/period;
    const minuteCoefficient = hourCoefficient/60;
    const hour = startHour + Math.floor((x - timeLineStart) / hourCoefficient);
    const minute = Math.floor((x - timeLineStart) / minuteCoefficient);

    return FormaliseTime(hour, minute);
};