import axios from "axios";
import {POST_RELATIONSHIP, GET_RELATIONSHIP_BY_ID, PUT_RELATIONSHIP, DELETE_RELATIONSHIP_BY_ID } from "../api/ApiEndpoints";
import {POST_RELATIONSHIP_ACTION, GET_RELATIONSHIP_ACTION, PUT_RELATIONSHIP_ACTION, DELETE_RELATIONSHIP_ACTION} from "./ActionTypes";

export function postRelationship(relationship, token) {
    return dispatch => {
        axios.post(POST_RELATIONSHIP, relationship, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: POST_RELATIONSHIP_ACTION, payload: response.data})
        });
    }
}

export function getRelationship(id, token) {
    return dispatch => {
        axios.get(GET_RELATIONSHIP_BY_ID(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: GET_RELATIONSHIP_ACTION, payload: response.data})
        });
    }
}

export function putRelationship(relationship, token) {
    return dispatch => {
        axios.put(PUT_RELATIONSHIP, relationship, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: PUT_RELATIONSHIP_ACTION, payload: response.data})
        });
    }
}

export function deleteRelationship(id, token) {
    return dispatch => {
        axios.delete(DELETE_RELATIONSHIP_BY_ID(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: DELETE_RELATIONSHIP_ACTION, payload: id})
        });
    }
}