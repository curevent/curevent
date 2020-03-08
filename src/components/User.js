import React from 'react';
import '../css/components/User.css'

function User() {
    return (
        <div className="user">
            <div className="user_img"/>
            <div className="user_name">Владислав Лапшин</div>
            <div className="user_status">Занят / до 21:40 / ещё 01:30</div>
        </div>
    );
}

export default User;