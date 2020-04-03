import axios from "axios";
import {POST_TAG, GET_TAG_BY_ID, PUT_TAG, DELETE_TAG_BY_ID } from "../api/ApiEndpoints";
import {POST_TAG_ACTION, GET_TAG_ACTION, PUT_TAG_ACTION, DELETE_TAG_ACTION} from "./ActionTypes";

export function postTag(tag, token) {
    return dispatch => {
        axios.post(POST_TAG, tag, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: POST_TAG_ACTION, payload: response.data})
        });
    }
}

export function getTag(id, token) {
    return dispatch => {
        axios.get(GET_TAG_BY_ID(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: GET_TAG_ACTION, payload: response.data})
        });
    }
}

export function putTag(tag, token) {
    return dispatch => {
        axios.put(PUT_TAG, tag, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: PUT_TAG_ACTION, payload: response.data})
        });
    }
}

export function deleteTag(id, token) {
    return dispatch => {
        axios.delete(DELETE_TAG_BY_ID(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: DELETE_TAG_ACTION, payload: response.data})
        });
    }
}