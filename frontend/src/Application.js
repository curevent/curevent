import React from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import {AuthPage} from "./pages/auth/AuthPage";
import './css/application.css'
import ProfilePage from "./pages/profile/ProfilePage";

function App() {
  return (
      <BrowserRouter>
          <div className="application-container">
              <Switch>
                <Route path="/auth" exact component={AuthPage}/>
                <Route path="/profile" component={ProfilePage}/>
              </Switch>
          </div>
      </BrowserRouter>
  );
}

export default App;
