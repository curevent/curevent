import {
    CREATE_TEMPLATE_EVENTS_ACTION,
    DELETE_TEMPLATE_AND_TEMPLATE_EVENTS_ACTION,
    DELETE_TEMPLATE_EVENTS_ACTION,
    GET_TEMPLATE_ACTION,
    POST_TEMPLATE_ACTION,
    PUT_TEMPLATE_AND_TEMPLATE_EVENTS_ACTION
} from "../constants/ActionTypes";

const initialState = {
    templateInfo: {
        id: null,
        ownerId: null,
        duration: null,
        title: null,
        description: null,
        latitude: null,
        longitude: null,

        privacy: [],
        tags: [],
        events: []
    }
};

export function templateReducer(state = initialState, action) {
    switch (action.type) {
        case GET_TEMPLATE_ACTION:
            return {...state, ...{templateInfo: action.payload}};
        case POST_TEMPLATE_ACTION:
            return {...state, ...{templateInfo: action.payload}};
        case CREATE_TEMPLATE_EVENTS_ACTION:
            return {...state, ...{templateInfo: action.payload}};
        case PUT_TEMPLATE_AND_TEMPLATE_EVENTS_ACTION:
            return {...state, ...{templateInfo: action.payload}};
        case DELETE_TEMPLATE_AND_TEMPLATE_EVENTS_ACTION:
            return {...state, ...initialState};
        case DELETE_TEMPLATE_EVENTS_ACTION:
            return {...state, ...{templateInfo: action.payload}};
        default:
            return state;
    }
}