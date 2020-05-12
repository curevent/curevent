import {
    CREATE_TEMPLATE_EVENTS_ACTION,
    DELETE_TEMPLATE_AND_TEMPLATE_EVENTS_ACTION,
    DELETE_TEMPLATE_EVENTS_ACTION,
    FILTER_USER_EVENTS_BY_TAGS_ACTION,
    FILTER_USER_EVENTS_IN_INTERVAL_BY_TAGS_ACTION, FILTER_USER_FRIENDS_EVENTS_BY_TAGS_ACTION,
    FILTER_USER_TEMPLATES_BY_TAGS_ACTION,
    GET_TEMPLATE_ACTION,
    POST_TEMPLATE_ACTION,
    PUT_TEMPLATE_AND_TEMPLATE_EVENTS_ACTION
} from "../constants/ActionTypes";
import {FILTER_USER_TEMPLATES_BY_TAGS} from "../constants/ApiEndpoints";

const initialState = {
    filteredTemplates: [],
    filteredUserEvents: [],
    filteredUserFriendsEvents:[]
};

export function templateReducer(state = initialState, action) {
    switch (action.type) {
        case FILTER_USER_TEMPLATES_BY_TAGS_ACTION:
            return {...state, ...{filteredTemplates: action.payload}};
        case FILTER_USER_EVENTS_BY_TAGS_ACTION:
            return {...state, ...{filteredUserEvents: action.payload}};
        case FILTER_USER_EVENTS_IN_INTERVAL_BY_TAGS_ACTION:
            return {...state, ...{filteredUserEvents: action.payload}};
        case FILTER_USER_FRIENDS_EVENTS_BY_TAGS_ACTION:
            return {...state, ...{filteredUserFriendsEvents: action.payload}};
        default:
            return state;
    }
}