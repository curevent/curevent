import React from "react";
import {NavLink} from "react-router-dom";
import {UserImage} from "../fields/UserImage";

export const SearchMenu = ({onClose, users}) => {

    const result = (users) => {
        if (users === null || users === undefined) {
            return "No one user was found."
        } else {
            return (
                users.map(user =>
                    <NavLink
                        className="user-found"
                        key={user.id}
                        to={`/user/${user.id}`}
                    >
                        <div className="found-icon">
                            <UserImage userInfo={user}/>
                        </div>
                        <div>{user.username}</div>
                    </NavLink>
                )
            );
        }
    };

    return (
        <div className="search-menu" onMouseLeave={onClose}>
            {result(users)}
        </div>
    );
};
