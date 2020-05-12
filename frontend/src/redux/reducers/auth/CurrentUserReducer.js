import {INVALIDATE_USER_INFO_ACTION, WHO_AM_I_ACTION} from "../../constants/ActionTypes";

const initialState = {
    userInfo: {
        id: null,
        username: null,
        email: null,
    }
};

export function currentUserReducer(state = initialState, action) {
    switch (action.type) {
        case WHO_AM_I_ACTION:
            console.log(action);
            return {...state, ...{userInfo: action.payload}};
        case INVALIDATE_USER_INFO_ACTION:
            console.log(action);
            return {...state, ...initialState};
        default:
            return state;
    }
}
