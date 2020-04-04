import {DELETE_EVENT_ACTION, GET_EVENT_ACTION, POST_EVENT_ACTION, PUT_EVENT_ACTION} from "../actions/ActionTypes";

const initialState = {
    events:[]
};

export function eventReducer(state = initialState, action) {
    switch (action.type) {
        case GET_EVENT_ACTION:
            return state.events.filter(event => event.id !== action.payload.id).push(action.payload);
        case POST_EVENT_ACTION:
            return {...state, events: [...state.events, action.payload]};
        case PUT_EVENT_ACTION:
            return state.events.filter(event => event.id !== action.payload.id).push(action.payload);
        case DELETE_EVENT_ACTION:
            return state.events.filter(event => event.id !== action.payload);
        default:
            return state;
    }
}
