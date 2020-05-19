import React, {Component, Fragment} from "react";
import {connect} from "react-redux";
import {getUser} from "../../redux/services/UserService";
import {saveUser} from "../../redux/actions/UserActions";
import {deleteTemplate} from "../../redux/services/TemplateService";

class CategoriesList extends Component {

    state = {};

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
            deleteTemplate(id).then(ignore => {
                const userId = this.props.userInfo.id;
                getUser(userId).then(user => {
                    this.props.saveUser(user);
                    this.setState(user);
                });
            });
        };

        if (this.props.categories.length === 0) {
            return <div className="none-message">You have no categories yet</div>
        } else {
            return this.props.categories.map(category =>
                <Fragment>
                    <div
                        id={category.id}
                        className="tag"
                        key={category.id}
                    >
                        {category.description}
                    </div>
                    <div className="flex-column">
                        <button
                            className="close-button"
                            style={{backgroundSize: "10px"}}
                            onClick={event => deleteHandler(event, category.id)}
                        />
                    </div>
                </Fragment>
            );
        }
    }
}

const mapStateToProps = state => {
    return {
        userInfo: state.currentUser.userInfo,
        categories: state.user.curUser.categories
    }
};

const mapDispatchToProps = {
    saveUser
};

export default connect(mapStateToProps, mapDispatchToProps)(CategoriesList);
