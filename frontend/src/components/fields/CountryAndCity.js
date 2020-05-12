import React from "react";

export const CountryAndCity = ({userInfo}) => {
    if (userInfo.country !== null
        && userInfo.city !== null) {
        return(
            <div className="field contact-info">
                {userInfo.country}, {userInfo.city}
            </div>
        );
    } else if (userInfo.country !== null) {
        return(
            <div className="field contact-info">
                {userInfo.country}
            </div>
        );
    } else if (userInfo.city !== null) {
        return(
            <div className="field contact-info">
                {userInfo.city}
            </div>
        );
    }
    return "";
};
