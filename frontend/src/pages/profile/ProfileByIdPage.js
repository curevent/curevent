import React, {Component} from "react";
import {connect} from "react-redux";
import {useParams} from "react-router-dom";

const ProfileByIdPage = () => {

    let { id } = useParams();

    return (
        <div className="profile-container">
            ID: {id}
        </div>
    );
};

const mapStateToProps = state => {
    return {
    }
};

const mapDispatchToProps = {

};

export default connect(mapStateToProps, mapDispatchToProps)(ProfileByIdPage);
