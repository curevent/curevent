import axios from "axios";
import {POST_EVENT, GET_EVENT_BY_ID, PUT_EVENT, DELETE_EVENT_BY_ID } from "../api/ApiEndpoints";
import {POST_EVENT_ACTION, GET_EVENT_ACTION, PUT_EVENT_ACTION, DELETE_EVENT_ACTION} from "./ActionTypes";

export function postEvent(event, token) {
    return dispatch => {
        axios.post(POST_EVENT, event, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: POST_EVENT_ACTION, payload: response.data})
        });
    }
}

export function getEvent(id, token) {
    return dispatch => {
        axios.get(GET_EVENT_BY_ID(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: GET_EVENT_ACTION, payload: response.data})
        });
    }
}

export function putEvent(event, token) {
    return dispatch => {
        axios.put(PUT_EVENT, event, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: PUT_EVENT_ACTION, payload: response.data})
        });
    }
}

export function deleteEvent(id, token) {
    return dispatch => {
        axios.delete(DELETE_EVENT_BY_ID(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: DELETE_EVENT_ACTION, payload: response.data})
        });
    }
}