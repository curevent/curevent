import React from 'react';
import '../commons/components/css/PorfilePage.css';
import TimeLine from "../commons/components/TimeLine";
import Profile from "../commons/components/User";
import Header from "../commons/components/Header";

function ProfilePage() {
    return (
        <div className="profile-page">
            <Header/>
            <div className="profile-container">
                <Profile/>
                <TimeLine/>
            </div>
        </div>
    );
}

export default ProfilePage;