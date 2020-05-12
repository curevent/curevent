import React, {Fragment} from "react";

export const NameAndUsername = ({userInfo}) => {

    if (userInfo.name !== null
        || userInfo.surname !== null) {
        return (
            <Fragment>
                <div className="field name">
                    {userInfo.name} {userInfo.surname}
                </div>
                <div className="field login">
                    {userInfo.username}
                </div>
            </Fragment>
        );
    } else {
        return (
            <div className="field name"> {userInfo.username} </div>
        );
    }
};
