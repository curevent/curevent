import {AUTH_ACTION, REGISTER_ACTION} from "../actions/ActionTypes";
import {postAuth, postRegister} from "../actions/AuthActions";

const initialState = {
    auth_token: null,
    refresh_token: null,
    token_type: "bearer ",
    expires_in: 0
};

export function authReducer(state = initialState, action) {
    switch (action.type) {
        case AUTH_ACTION:
            return postAuth(action.authInfo);
        case REGISTER_ACTION:
            return postRegister(action.registerInfo);
        default:
            return state;
    }
}