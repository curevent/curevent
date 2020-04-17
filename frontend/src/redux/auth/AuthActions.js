import {
    GET_AUTH_ACTION,
    GET_REFRESH_ACTION,
    GET_REGISTER_ACTION,
    INVALIDATE_AUTH_ACTION,
    WHO_AM_I_ACTION
} from "../constants/ActionTypes";

export const invalidateAuth = (ignore) => {
    return dispatch => {
        dispatch({type: INVALIDATE_AUTH_ACTION})
    };
};

export function auth(tokens) {
    return dispatch => {
        dispatch({type: GET_AUTH_ACTION, payload: tokens});
    }
}

export function register(tokens) {
    return dispatch => {
        dispatch({type: GET_REGISTER_ACTION, payload: tokens});
    }
}

export function currentUser(userInfo) {
    return dispatch => {
        dispatch({type: WHO_AM_I_ACTION, payload: userInfo});
    }
}

export function refresh(tokens) {
    return dispatch => {
        dispatch({type: GET_REFRESH_ACTION, payload: tokens});
    }
}
