import {
    DELETE_CATEGORY_ACTION,
    GET_CATEGORY_ACTION,
    POST_CATEGORY_ACTION,
    PUT_CATEGORY_ACTION
} from "../constants/ActionTypes";

export function postCategory(category) {
    return dispatch => {
        dispatch({type: POST_CATEGORY_ACTION, payload: category});
    }
}

export function getCategory(id) {
    return dispatch => {
        dispatch({type: GET_CATEGORY_ACTION, payload: id});
    }
}

export function putCategory(category) {
    return dispatch => {
        dispatch({type: PUT_CATEGORY_ACTION, payload: category});
    }
}

export function deleteCategory(id) {
    return dispatch => {
        dispatch({type: DELETE_CATEGORY_ACTION, payload: id});
    }
}
