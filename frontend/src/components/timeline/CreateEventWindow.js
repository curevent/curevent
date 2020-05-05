import React, {Fragment, useState} from "react";
import {getTokens} from "../../utils/localStorageUtils";
import {postTemplate} from "../../redux/actions/TemplateActions";
import {getUser} from "../../redux/actions/UserActions";
import {connect} from "react-redux";

const CreateEventWindow = ({onClose, userInfo, postTemplate, getUser}) => {

    const [template, setTemplate] = useState({});

    const submitHandler = (event) => {
        const newTemplate = template;
        newTemplate.ownerId = userInfo.id;
        const tokens = getTokens();
        postTemplate(newTemplate, tokens.access);
        getUser(userInfo.id, tokens.access);
        onClose()
    };

    const changeStateHandler = event => {
        event.persist();
        setTemplate(prevState => ({...prevState, ...{[event.target.id]: event.target.value}}));
    };

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
                        />
                    </div>
                    <div className="field">
                        <div className="template-layout">Description:</div>
                        <textarea
                            id="description"
                            value={template.description}
                            className="template-input template-textarea"
                            onChange={changeStateHandler}
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
    postTemplate,
    getUser
};

export default connect(mapStateToProps, mapDispatchToProps)(CreateEventWindow);
