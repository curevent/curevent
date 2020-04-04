import {DELETE_TAG_ACTION, GET_TAG_ACTION, POST_TAG_ACTION, PUT_TAG_ACTION} from "../actions/ActionTypes";

const initialState = {
    tags:[]
};

export function tagReducer(state = initialState, action) {
    switch (action.type) {
        case GET_TAG_ACTION:
            return state.tags.filter(tag => tag.id !== action.payload.id).push(action.payload);
        case POST_TAG_ACTION:
            return {...state, tags: [...state.tags, action.payload]};
        case PUT_TAG_ACTION:
            return state.tags.filter(tag => tag.id !== action.payload.id).push(action.payload);
        case DELETE_TAG_ACTION:
            return state.tags.filter(tag => tag.id !== action.payload);
        default:
            return state;
    }
}