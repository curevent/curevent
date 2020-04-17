import React, {Component} from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import {AuthPage} from "./pages/auth/AuthPage";
import './css/application.css'
import ProfilePage from "./pages/profile/ProfilePage";
import {connect} from "react-redux";

class Application extends Component {

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

export default connect(null, null)(Application);
