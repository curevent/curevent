import React from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import './css/App.css';
import './css/ComponentsDefault.css'
import AuthPage from "./pages/AuthPage";
import ProfilePage from "./pages/ProfilePage";
import Footer from "./components/Footer";

export const AuthContext = React.createContext({
    isAuth: false,
    toggleAuth: () => {},
});

function App() {
  return (
      <BrowserRouter>
          <div className="App">
              <Switch>
                  <Route path="/" exact component={AuthPage}/>
                  <Route path="/profile" component={ProfilePage}/>
              </Switch>
              <Footer/>
          </div>
      </BrowserRouter>
  );
}

export default App;
