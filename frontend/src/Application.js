import React, {Component} from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import AuthPage from "./pages/auth/AuthPage";
import './css/application.css'
import {connect} from "react-redux";
import {refresh} from "./redux/actions/AuthActions";
import {getRefresh} from "./redux/services/AuthService";
import {getTokens} from "./utils/localStorageUtils";
import MyProfilePage from "./pages/profile/MyProfilePage";
import ProfileByIdPage from "./pages/profile/ProfileByIdPage";
import Header from "./components/header/Header";
import SettingsPage from "./pages/settings/SettingsPage";
import Repository from "./components/repository/Repository";
import FriendsPage from "./pages/friends/FriendsPage";

class Application extends Component {

    componentWillMount() {
        const tokens = getTokens();
        if (tokens.refresh != null) {
            getRefresh().then(tokens => {
                console.log(tokens);
                this.props.refresh(tokens);
            });
        }
    }

    render() {

        return (
            <BrowserRouter>
                <div className="application-container">
                    {(this.props.isAuth && this.props.user.id === this.props.page.id) && <Repository/>}
                    {this.props.isAuth && this.props.isMinimized && <div className="space"/>}
                    {this.props.isAuth && this.props.user.id !== this.props.page.id && !this.props.isMinimized && <div className="space"/>}
                    <div className="main-space">
                        {this.props.isAuth && <Header/>}
                        <Switch>
                            <Route path="/" exact component={AuthPage}/>
                            <Route path="/profile" component={MyProfilePage}/>
                            <Route path="/friends" component={FriendsPage}/>
                            <Route path="/settings" component={SettingsPage}/>
                            <Route path="/user/:id" component={ProfileByIdPage}/>
                        </Switch>
                    </div>
                </div>
            </BrowserRouter>
        );
    }
}

const mapStateToProps = state => {
    return {
        isAuth: state.auth.isAuth,
        user: state.currentUser.userInfo,
        isMinimized: state.repo.isMinimized,
        page: state.user.curUser
    }
};

const mapDispatchToProps = {
    refresh,
};

export default connect(mapStateToProps, mapDispatchToProps)(Application);
