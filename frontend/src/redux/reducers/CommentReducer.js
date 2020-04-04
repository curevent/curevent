import {
    DELETE_COMMENT_ACTION,
    GET_COMMENT_ACTION,
    POST_COMMENT_ACTION,
    PUT_COMMENT_ACTION
} from "../actions/ActionTypes";

const initialState = {
    comments:[]
};

export function commentReducer(state = initialState, action) {
    switch (action.type) {
        case GET_COMMENT_ACTION:
            return state.comments.map(comment => {
                return comment.id !== action.payload.id ? comment : action.payload});
        case POST_COMMENT_ACTION:
            return {...state, comments: [...state.comments, action.payload]};
        case PUT_COMMENT_ACTION:
            return state.comments.map(comment => {
                return comment.id !== action.payload.id ? comment : action.payload});
        case DELETE_COMMENT_ACTION:
            return state.comments.filter(comment => comment.id !== action.payload);
        default:
            return state;
    }
}