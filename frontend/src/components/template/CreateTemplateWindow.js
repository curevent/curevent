import React, {Component, Fragment, useState} from "react";
import {connect} from "react-redux";
import {saveUser} from "../../redux/actions/UserActions";
import {getUser} from "../../redux/services/UserService";
import {postTemplate} from "../../redux/services/TemplateService";

const CreateTemplateWindow = ({onClose, userInfo, saveUser}) => {

    const [template, setTemplate] = useState({});

    const submitHandler = (event) => {
        const newTemplate = template;
        newTemplate.ownerId = userInfo.id;
        postTemplate(newTemplate).then(ignore => {
            getUser(userInfo.id).then(user => saveUser(user));
        });
        onClose();
    };

    const changeStateHandler = event => {
        event.persist();
        console.log(event.target.value);
        setTemplate(prevState => ({...prevState, ...{[event.target.id]: event.target.value}}));
    };

    console.log(template);

    return (
        <Fragment>
            <div className="create-template-window">
                <div className="create-template-container">
                    <div className="ct-title">
                        <div className="ct-title-message">Create template </div>
                        <button className="close-button" onClick={onClose}/>
                    </div>
                    <div className="field">
                        <div className="template-layout">Title:</div>
                        <input
                            id="title"
                            value={template.title}
                            className="template-input"
                            onChange={changeStateHandler}
                            autoComplete="off"
                        />
                    </div>
                    <div className="field">
                        <div className="template-layout">Description:</div>
                        <textarea
                            id="description"
                            value={template.description}
                            className="template-input template-textarea"
                            onChange={changeStateHandler}
                            autoComplete="off"
                        />
                    </div>
                    <div className="field">
                        <div className="template-layout">Duration:</div>
                        <input
                            id="duration"
                            type="number"
                            placeholder="Time your event in minutes"
                            value={template.duration}
                            className="template-input"
                            onChange={changeStateHandler}
                            autoComplete="off"
                        />
                    </div>
                    <div className="template-window-buttons">
                        <button className="auth-button" onClick={submitHandler}>Create</button>
                    </div>
                </div>
            </div>
        </Fragment>
    );
};

const mapStateToProps = state => {
    return {
        userInfo: state.currentUser.userInfo,
    }
};

const mapDispatchToProps = {
    saveUser
};

export default connect(mapStateToProps, mapDispatchToProps)(CreateTemplateWindow);
