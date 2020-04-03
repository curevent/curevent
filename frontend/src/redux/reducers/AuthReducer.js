import {AUTH_ACTION, REGISTER_ACTION} from "../actions/ActionTypes";
import {postAuth, postRegister} from "../actions/AuthActions";

const initialState = {
    tokens:{}
};

export function authReducer(state = initialState, action) {
    console.log(action);
    switch (action.type) {
        case AUTH_ACTION:
            return {...state, tokens: action.payload};
        case REGISTER_ACTION:
            return {...state, tokens: action.payload};
        default:
            return state;
    }
}