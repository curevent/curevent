import {DELETE_EVENT_ACTION, GET_EVENT_ACTION, POST_EVENT_ACTION, PUT_EVENT_ACTION} from "../constants/ActionTypes";

const initialState = {
    eventInfo: {
        id: null,
        ownerId: null,
        time: null,
        duration: null,
        title: null,
        description: null,
        templateId: null,
        latitude: null,
        longitude: null,

        privacy: [],
        tags: [],
        comments: [],
        blackList: []
    }
};

export function eventReducer(state = initialState, action) {
    switch (action.type) {
        case GET_EVENT_ACTION:
            return {...state, ...{eventInfo: action.payload}};
        case POST_EVENT_ACTION:
            return {...state, ...{eventInfo: action.payload}};
        case PUT_EVENT_ACTION:
            return {...state, ...{eventInfo: action.payload}};
        case DELETE_EVENT_ACTION:
            return {...state, ...initialState};
        default:
            return state;
    }
}
