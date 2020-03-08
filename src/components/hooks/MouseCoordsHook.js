import {useMouseMove} from "react-use-mouse-move";
import React from "react";

const MouseCoordsHook = (props) => {
    const pos = useMouseMove(1);

    const hour = 12 + Math.floor((pos.x - 120) / 140);

    const min = Math.floor((pos.x+20)*(60/140))%60;

    return (
        <div id="time">
            <span style={{marginLeft: + (pos.x-120) + 'px', marginTop:'10px', display:"block", position: "absolute"}}>
                {hour < 24 || hour < 0 ? hour : '00'}:
                {min > 59 || min < 0 ? '00' : (min < 10 ? '0' + min : min)}
            </span>
        </div>
    );
}

export default MouseCoordsHook;