import axios from "axios";
import {POST_CATEGORY, GET_CATEGORY_BY_ID, PUT_CATEGORY, DELETE_CATEGORY_BY_ID } from "../api/ApiEndpoints";
import {POST_CATEGORY_ACTION, GET_CATEGORY_ACTION, PUT_CATEGORY_ACTION, DELETE_CATEGORY_ACTION} from "./ActionTypes";

export function postCategory(category, token) {
    return dispatch => {
        axios.post(POST_CATEGORY, category, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: POST_CATEGORY_ACTION, payload: response.data})
        });
    }
}

export function getCategory(id, token) {
    return dispatch => {
        axios.get(GET_CATEGORY_BY_ID(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: GET_CATEGORY_ACTION, payload: response.data})
        });
    }
}

export function putCategory(category, token) {
    return dispatch => {
        axios.put(PUT_CATEGORY, category, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: PUT_CATEGORY_ACTION, payload: response.data})
        });
    }
}

export function deleteCategory(id, token) {
    return dispatch => {
        axios.delete(DELETE_CATEGORY_BY_ID(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: DELETE_CATEGORY_ACTION, payload: response.data})
        });
    }
}