import {
    DELETE_COMMENT_ACTION,
    GET_COMMENT_ACTION,
    POST_COMMENT_ACTION,
    PUT_COMMENT_ACTION
} from "../constants/ActionTypes";

const initialState = {
    commentInfo: {
        id: null,
        eventId: null,
        ownerId: null,
        description: null
    }
};

export function commentReducer(state = initialState, action) {
    switch (action.type) {
        case GET_COMMENT_ACTION:
            return {...state, ...{commentInfo: action.payload}};
        case POST_COMMENT_ACTION:
            return {...state, ...{commentInfo: action.payload}};
        case PUT_COMMENT_ACTION:
            return {...state, ...{commentInfo: action.payload}};
        case DELETE_COMMENT_ACTION:
            return {...state, ...initialState};
        default:
            return state;
    }
}
