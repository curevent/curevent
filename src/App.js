import React from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import './commons/components/css/App.css';
import './commons/components/css/ComponentsDefault.css'
import LandingPage from "./landing/LandingPage";
import ProfilePage from "./profile/ProfilePage";
import Footer from "./commons/components/Footer";

export const AuthContext = React.createContext({
    isAuth: false,
    toggleAuth: () => {},
});

function App() {
  return (
      <BrowserRouter>
          <div className="App">
              <Switch>
                  <Route path="/" exact component={LandingPage}/>
                  <Route path="/profile" component={ProfilePage}/>
                  <Route path="/search" component={ProfilePage}/>
              </Switch>
              <Footer/>
          </div>
      </BrowserRouter>
  );
}

export default App;
