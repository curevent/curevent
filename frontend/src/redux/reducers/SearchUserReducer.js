import {
    DELETE_USER_ACTION,
    DELETE_USER_EVENTS_ACTION,
    DELETE_USER_FRIENDS_ACTION,
    DELETE_USER_TEMPLATES_ACTION,
    GET_USER_ACTION,
    GET_USER_EVENTS_IN_INTERVAL_ACTION,
    GET_USER_FRIENDS_ACTION,
    GET_USER_FRIENDS_EVENTS_IN_INTERVAL_ACTION, GET_USERS_ACTION,
    POST_USER_ACTION,
    PUT_USER_ACTION
} from "../constants/ActionTypes";
import {FIND_USER_BY_NAME_AND_SURNAME, FIND_USER_BY_USERNAME} from "../constants/ApiEndpoints";

const initialState = {
    foundUsers: []
};

export function searchUserReducer(state = initialState, action) {
    switch (action.type) {
        case FIND_USER_BY_NAME_AND_SURNAME:
            return {...state, ...{foundUsers: action.payload}};
        case FIND_USER_BY_USERNAME:
            return {...state, ...{foundUsers: action.payload}};
        default:
            return state;
    }
}
