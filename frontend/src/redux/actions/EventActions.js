import {DELETE_EVENT_ACTION, GET_EVENT_ACTION, POST_EVENT_ACTION, PUT_EVENT_ACTION} from "../constants/ActionTypes";

export function postEvent(event) {
    return dispatch => {
        dispatch({type: POST_EVENT_ACTION, payload: event});
    }
}

export function getEvent(id) {
    return dispatch => {
        dispatch({type: GET_EVENT_ACTION, payload: id});
    }
}

export function putEvent(event, token) {
    return dispatch => {
        dispatch({type: PUT_EVENT_ACTION, payload: event});
    }
}

export function deleteEvent(id) {
    return dispatch => {
        dispatch({type: DELETE_EVENT_ACTION, payload: id});
    }
}
