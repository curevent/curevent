import {
    NAV_STATUS_AUTH_ACTION,
    NAV_STATUS_PROFILE_ACTION,
    NAV_STATUS_REGISTER_ACTION,
    NAV_STATUS_SETTINGS_ACTION
} from "../constants/ActionTypes";

const initialState = {
    status: {
        visibility: false,
        page: "Authentication",
    }
};

export function navReducer(state = initialState, action) {
    switch (action.type) {
        case NAV_STATUS_AUTH_ACTION:
        case NAV_STATUS_REGISTER_ACTION:
        case NAV_STATUS_PROFILE_ACTION:
        case NAV_STATUS_SETTINGS_ACTION:
            console.log(action);
            return {...state, ...{status: action.payload}};
        default:
            return state;
    }
}
