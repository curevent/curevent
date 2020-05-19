import React from "react";

export const UserImage = ({userInfo}) => {
    if (userInfo.image === null) {
        return (
            <div className="user-image">
                {userInfo.username != null && userInfo.username.slice(0, 1).toUpperCase()}
            </div>
        );
    } else {
        return (
            <div
                className="user-image"
                style={{
                    backgroundImage: `url(${userInfo.image})`,
                    backgroundSize: `80%`,
                    backgroundPosition: "center",
                    backgroundRepeat: "no-repeat"
                }}
            />
        );
    }
};
