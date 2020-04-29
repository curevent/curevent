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
import Header from "./components/Header";
import TemplatesRepository from "./components/TemplatesRepository";

class Application extends Component {

    componentDidMount() {
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
                    <div className="main-content-container">
                        {this.props.isAuth && <Header/>}
                        <Switch>
                            <Route path="/" exact component={AuthPage}/>
                            <Route path="/profile" component={MyProfilePage}/>
                            <Route path="/user/:id" component={ProfileByIdPage}/>
                        </Switch>
                    </div>
                    {this.props.isAuth && <TemplatesRepository/>}
                </div>
            </BrowserRouter>
        );
    }
}

const mapStateToProps = state => {
    return {
        isAuth: state.auth.isAuth,
    }
};

const mapDispatchToProps = {
    refresh,
};

export default connect(mapStateToProps, mapDispatchToProps)(Application);
