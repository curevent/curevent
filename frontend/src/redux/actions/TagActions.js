import {DELETE_TAG_ACTION, GET_TAG_ACTION, POST_TAG_ACTION, PUT_TAG_ACTION} from "../constants/ActionTypes";

export function postTag(tag) {
    return dispatch => {
        dispatch({type: POST_TAG_ACTION, payload: tag});
    }
}

export function getTag(id) {
    return dispatch => {
        dispatch({type: GET_TAG_ACTION, payload: id});
    }
}

export function putTag(tag) {
    return dispatch => {
        dispatch({type: PUT_TAG_ACTION, payload: tag});
    }
}

export function deleteTag(id) {
    return dispatch => {
        dispatch({type: DELETE_TAG_ACTION, payload: id});
    }
}
