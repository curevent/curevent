import React, {Component} from "react";
import {connect} from "react-redux";
import {Redirect, useParams} from "react-router-dom";

const ProfileByIdPage = ({isAuth}) => {

    let { id } = useParams();

    return (
        <div className="profile-container">
            {!isAuth && <Redirect to="/"/>}
            ID: {id}
        </div>
    );
};

const mapStateToProps = state => {
    return {
        isAuth: state.auth.isAuth,
    }
};

const mapDispatchToProps = {

};

export default connect(mapStateToProps, mapDispatchToProps)(ProfileByIdPage);
