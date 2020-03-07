import React from 'react';
import '../css/components/Profile.css'

function Profile() {
    return (
        <div className="profile-content">
            <div className="user">
                <div className="user_img"/>
                <div className="user_name">Владислав Лапшин</div>
                <div className="user_status">Занят</div>
            </div>
        </div>
    );
}

export default Profile;