import {AUTH_ACTION, REGISTER_ACTION, WHO_AM_I_ACTION} from "../actions/ActionTypes";

const initialState = {
    tokens:{},
    userInfo:{}
};

export function authReducer(state = initialState, action) {
    console.log(action);
    switch (action.type) {
        case AUTH_ACTION:
            return {...state, ...{tokens: action.payload}};
        case REGISTER_ACTION:
            return {...state, ...{tokens: action.payload}};
        case WHO_AM_I_ACTION:
            return {...state, ...{userInfo: action.payload}};
        default:
            return state;
    }
}