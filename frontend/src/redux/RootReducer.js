import {combineReducers} from "redux";
import {tokensReducer} from "./reducers/auth/TokensReducer";
import {currentUserReducer} from "./reducers/auth/CurrentUserReducer";
import {navReducer} from "./reducers/NavReducer";

export const rootReducer = combineReducers(
    {
        auth: tokensReducer,
        nav: navReducer,
        currentUser: currentUserReducer
    }
);
