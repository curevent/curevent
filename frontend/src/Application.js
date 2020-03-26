import React from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import {AuthPage} from "./pages/auth/AuthPage";

function App() {
  return (
      <BrowserRouter>
          <div className="main-application-container">
              <Switch>
                <Route path="/auth" exact component={AuthPage}/>
              </Switch>
          </div>
      </BrowserRouter>
  );
}

export default App;
