import React from 'react';
import ReactDOM from 'react-dom';
import App from './Application';
import * as serviceWorker from './serviceWorker';
import {compose, createStore} from "redux";
import {rootReducer} from "./redux/reducers/RootReducer";
import {Provider} from "react-redux";

const store = createStore(rootReducer, compose(
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
));

ReactDOM.render(
    <Provider store={store}>
        <App/>
    </Provider>,
    document.getElementById('root')
);

serviceWorker.unregister();
