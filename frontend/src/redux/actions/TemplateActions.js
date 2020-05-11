import {
    CREATE_TEMPLATE_EVENTS_ACTION,
    DELETE_TEMPLATE_EVENTS_ACTION,
    EXPAND_REPOSITORY_ACTION,
    GET_TEMPLATE_ACTION,
    MINIMALIZE_REPOSITORY_ACTION,
    POST_TEMPLATE_ACTION,
    PUT_TEMPLATE_AND_TEMPLATE_EVENTS_ACTION
} from "../constants/ActionTypes";

export function postTemplate(template) {
    return dispatch => {
        dispatch({type: POST_TEMPLATE_ACTION, payload: template});
    }
}

export function createTemplateEvents(repeatForm, id) {
    return dispatch => {
        dispatch({type: CREATE_TEMPLATE_EVENTS_ACTION, payload: {repeatForm:repeatForm, id:id}});
    }
}

export function getTemplate(template) {
    return dispatch => {
        dispatch({type: GET_TEMPLATE_ACTION, payload: template});
    }
}

export function putTemplateAndTemplateEvents(template) {
    return dispatch => {
        dispatch({type: PUT_TEMPLATE_AND_TEMPLATE_EVENTS_ACTION, payload: template});
    }
}

export function deleteTemplateAndTemplateEvents(id) {
    return dispatch => {
        dispatch({type: DELETE_TEMPLATE_EVENTS_ACTION, payload: id});
    }
}

export function deleteTemplateEvents(id) {
    return dispatch => {
        dispatch({type: DELETE_TEMPLATE_EVENTS_ACTION, payload: id});
    }
}
