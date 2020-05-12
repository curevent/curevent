import React from "react";

export const UserImage = ({userInfo}) => {
    return (
        <div className="user-image">
            {userInfo.username != null && userInfo.username.slice(0, 1).toUpperCase()}
        </div>
    );
};
