import {
    DELETE_COMMENT_ACTION,
    GET_COMMENT_ACTION,
    POST_COMMENT_ACTION,
    PUT_COMMENT_ACTION
} from "../constants/ActionTypes";

export function postComment(comment) {
    return dispatch => {
        dispatch({type: POST_COMMENT_ACTION, payload: comment});
    }
}

export function getComment(id) {
    return dispatch => {
        dispatch({type: GET_COMMENT_ACTION, payload: id});
    }
}

export function putComment(comment) {
    return dispatch => {
        dispatch({type: PUT_COMMENT_ACTION, payload: comment});
    }
}

export function deleteComment(id) {
    return dispatch => {
        dispatch({type: DELETE_COMMENT_ACTION, payload: id});
    }
}
