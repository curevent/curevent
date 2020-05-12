import React, {Component, Fragment} from "react";
import {connect} from "react-redux";
import {getUser} from "../../redux/services/UserService";
import {saveUser} from "../../redux/actions/UserActions";
import {deleteTemplate} from "../../redux/services/TemplateService";
import {deleteTag, postTag} from "../../redux/services/TagService";

class TagsList extends Component {

    state = {
        tag: {
        description: ""
        }
    };

    componentDidMount() {
        const id = this.props.userInfo.id;
        getUser(id).then(user => {
            this.props.saveUser(user);
            this.setState(user);
        });
    }

    render() {

        const deleteHandler = (event, id) => {
            event.preventDefault();
            deleteTag(id).then(ignore => {
                const userId = this.props.userInfo.id;
                getUser(userId).then(user => {
                    this.props.saveUser(user);
                    this.setState(user);
                });
            });
        };

        const createHandler = (event) => {
            event.preventDefault();
            const tag = {description: this.state.description};
            const userId = this.props.userInfo.id;
            tag.ownerId = userId;
            console.log(tag);
            postTag(tag).then(ignore => {
                getUser(userId).then(user => {
                    this.props.saveUser(user);
                    this.setState(user);
                });
            });
        };

        const changeStateHandler = (event) => {
            event.preventDefault();
            this.setState({[event.target.id]: event.target.value});
        };

        const createTag = () => {
            return (
                <div className="create-panel">
                    <input
                        id="description"
                        value={this.state.description}
                        className="repository-input"
                        onChange={changeStateHandler}
                        placeholder="New tag"
                        autoComplete="off"
                    />
                    <button
                        className="create-button"
                        onClick={createHandler}
                    >
                        Create
                    </button>
                </div>
            )
        };

        const list = () => {
            if (this.props.tags.length === 0) {
                return (
                    <Fragment>
                        <div className="none-message">You have no tags yet</div>
                    </Fragment>
                );
            } else {
                return this.props.tags.map(tag =>
                    <Fragment>
                        <div
                            id={tag.id}
                            className="tag"
                            key={tag.id}
                            style={{background: `#${tag.id.slice(0, 6)}`}}
                        >
                            {tag.description}
                        </div>
                        <div className="flex-column">
                            <button
                                className="close-button"
                                style={{backgroundSize: "10px"}}
                                onClick={event => deleteHandler(event, tag.id)}
                            />
                        </div>
                    </Fragment>
                );
            }
        };

        return (
            <Fragment>
                {createTag()}
                {list()}
            </Fragment>
        );
    }
}

const mapStateToProps = state => {
    return {
        userInfo: state.currentUser.userInfo,
        tags: state.user.curUser.tags
    }
};

const mapDispatchToProps = {
    saveUser
};

export default connect(mapStateToProps, mapDispatchToProps)(TagsList);
