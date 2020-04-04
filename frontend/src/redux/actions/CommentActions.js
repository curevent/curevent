import axios from "axios";
import {POST_COMMENT, GET_COMMENT_BY_ID, PUT_COMMENT, DELETE_COMMENT_BY_ID } from "../api/ApiEndpoints";
import {POST_COMMENT_ACTION, GET_COMMENT_ACTION, PUT_COMMENT_ACTION, DELETE_COMMENT_ACTION} from "./ActionTypes";

export function postComment(comment, token) {
    return dispatch => {
        axios.post(POST_COMMENT, comment, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: POST_COMMENT_ACTION, payload: response.data})
        });
    }
}

export function getComment(id, token) {
    return dispatch => {
        axios.get(GET_COMMENT_BY_ID(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: GET_COMMENT_ACTION, payload: response.data})
        });
    }
}

export function putComment(comment, token) {
    return dispatch => {
        axios.put(PUT_COMMENT, comment, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: PUT_COMMENT_ACTION, payload: response.data})
        });
    }
}

export function deleteComment(id, token) {
    return dispatch => {
        axios.delete(DELETE_COMMENT_BY_ID(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: DELETE_COMMENT_ACTION, payload: id})
        });
    }
}