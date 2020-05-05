import React, {Component} from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import AuthPage from "./pages/auth/AuthPage";
import './css/application.css'
import ProfilePage from "./pages/profile/ProfilePage";
import {connect} from "react-redux";
import {refresh} from "./redux/auth/AuthActions";
import {getRefresh} from "./redux/auth/AuthService";
import {getTokens} from "./utils/localStorageUtils";

class Application extends Component {

    componentDidMount() {
        const tokens = getTokens();
        if (tokens.refresh != null) {
            getRefresh().then(this.props.refresh);
        }
    }

    render() {
        return (
            <BrowserRouter>
                <div className="application-container">
                    <Switch>
                        <Route path="/" exact component={AuthPage}/>
                        <Route path="/profile" component={ProfilePage}/>
                        {/*<Route path="/user/:id" component={UserPage}/>*/}
                    </Switch>
                </div>
            </BrowserRouter>
        );
    }
}


const mapDispatchToProps = {
    refresh,
};

export default connect(null, mapDispatchToProps)(Application);
