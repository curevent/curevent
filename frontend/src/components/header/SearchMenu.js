import React from "react";
import {NavLink} from "react-router-dom";
import {UserImage} from "../fields/UserImage";
import AddFriendButton from "./AddFriendButton";

export const SearchMenu = ({onClose, users}) => {

    const result = (users) => {
        if (users === null || users === undefined) {
            return "No one user was found."
        } else {
            return (
                users.map(user =>
                    <div className="flex-row">
                        <NavLink
                            className="user-found"
                            key={user.id}
                            to={`/user/${user.id}`}
                        >
                            <div className="found-icon">
                                <UserImage userInfo={user}/>
                            </div>
                            <div className="found-info">
                                <div>{user.username}</div>
                                <div className="found-mail">{user.email}</div>
                            </div>
                        </NavLink>
                        <AddFriendButton id={user.id}/>
                    </div>
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
