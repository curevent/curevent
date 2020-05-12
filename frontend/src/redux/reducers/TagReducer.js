import {DELETE_TAG_ACTION, GET_TAG_ACTION, POST_TAG_ACTION, PUT_TAG_ACTION} from "../constants/ActionTypes";

const initialState = {
    tagInfo: {
        id: null,
        description: null
    }
};

export function tagReducer(state = initialState, action) {
    switch (action.type) {
        case GET_TAG_ACTION:
            return {...state, ...{tagInfo: action.payload}};
        case POST_TAG_ACTION:
            return {...state, ...{tagInfo: action.payload}};
        case PUT_TAG_ACTION:
            return {...state, ...{tagInfo: action.payload}};
        case DELETE_TAG_ACTION:
            return {...state, ...initialState};
        default:
            return state;
    }
}
