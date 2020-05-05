import {GET_RELATIONSHIP_ACTION, POST_RELATIONSHIP_ACTION, PUT_RELATIONSHIP_ACTION} from "../constants/ActionTypes";

export function postRelationship(relationship) {
    return dispatch => {
        dispatch({type: POST_RELATIONSHIP_ACTION, payload: relationship});
    }
}

export function getRelationship(id) {
    return dispatch => {
        dispatch({type: GET_RELATIONSHIP_ACTION, payload: id});
    }
}

export function putRelationship(relationship) {
    return dispatch => {
        dispatch({type: PUT_RELATIONSHIP_ACTION, payload: relationship});
    }
}

export function deleteRelationship(id) {
    return dispatch => {
        dispatch({type: POST_RELATIONSHIP_ACTION, payload: id});
    }
}
