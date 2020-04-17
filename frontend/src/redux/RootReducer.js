import {combineReducers} from "redux";
import {tokensReducer} from "./auth/TokensReducer";
import {currentUserReducer} from "./auth/CurrentUserReducer";

export const rootReducer = combineReducers(
    {
        auth: tokensReducer,
        currentUser: currentUserReducer
    }
);
