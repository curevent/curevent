import {
    GET_AUTH_ACTION,
    GET_REFRESH_ACTION,
    GET_REGISTER_ACTION,
    INVALIDATE_AUTH_ACTION,
} from "../../constants/ActionTypes";

const initialState = {
    tokens: {
        access_token: null,
        refresh_token: null
    },
    isAuth: false
};

export function tokensReducer(state = initialState, action) {
    switch (action.type) {
        case GET_AUTH_ACTION:
        case GET_REGISTER_ACTION:
        case GET_REFRESH_ACTION:
            console.log(action);
            return {...state, ...{tokens: action.payload, isAuth: true}};
        case INVALIDATE_AUTH_ACTION:
            console.log(action);
            return {...state, ...{tokens: "", isAuth: false}};
        default:
            return state;
    }
}
