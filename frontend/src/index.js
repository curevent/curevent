import React from 'react';
import ReactDOM from 'react-dom';
import * as serviceWorker from './serviceWorker';
import {applyMiddleware, compose, createStore} from "redux";
import {rootReducer} from "./redux/RootReducer";
import {Provider} from "react-redux";
import thunk from "redux-thunk";
import Application from "./Application";


const store = createStore(rootReducer, compose(
    applyMiddleware(thunk),
    // window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
));

const app = () => {
    return (
        <Provider store={store}>
            <Application/>
        </Provider>
    );
};

ReactDOM.render(app(), document.getElementById('root'));
serviceWorker.unregister();
