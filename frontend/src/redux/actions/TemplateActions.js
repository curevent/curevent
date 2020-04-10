import axios from "axios";
import {
    DELETE_TEMPLATE_AND_TEMPLATE_EVENTS, DELETE_TEMPLATE_EVENTS,
    GET_TEMPLATE_BY_ID, POST_REPEAT_FORM_AND_CREATE_TEMPLATE_EVENTS,
    POST_TEMPLATE,
    PUT_TEMPLATE_AND_TEMPLATE_EVENTS
} from "../api/ApiEndpoints";
import {
    CREATE_TEMPLATE_EVENTS_ACTION,
    DELETE_TEMPLATE_AND_TEMPLATE_EVENTS_ACTION, DELETE_TEMPLATE_EVENTS_ACTION,
    GET_TEMPLATE_ACTION,
    POST_TEMPLATE_ACTION,
    PUT_TEMPLATE_AND_TEMPLATE_EVENTS_ACTION
} from "./ActionTypes";

export function postTemplate(template, token) {
    return dispatch => {
        axios.post(POST_TEMPLATE, template, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: POST_TEMPLATE_ACTION, payload: response.data})
        });
    }
}

export function createTemplateEvents(repeatForm, id, token) {
    return dispatch => {
        axios.post(POST_REPEAT_FORM_AND_CREATE_TEMPLATE_EVENTS(id), repeatForm, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: CREATE_TEMPLATE_EVENTS_ACTION, payload: response.data})
        });
    }
}

export function getTemplate(id, token) {
    return dispatch => {
        axios.get(GET_TEMPLATE_BY_ID(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: GET_TEMPLATE_ACTION, payload: response.data})
        });
    }
}

export function putTemplateAndTemplateEvents(template, token) {
    return dispatch => {
        axios.put(PUT_TEMPLATE_AND_TEMPLATE_EVENTS, template, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: PUT_TEMPLATE_AND_TEMPLATE_EVENTS_ACTION, payload: response.data})
        });
    }
}

export function deleteTemplateAndTemplateEvents(id, token) {
    return dispatch => {
        axios.delete(DELETE_TEMPLATE_AND_TEMPLATE_EVENTS(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: DELETE_TEMPLATE_AND_TEMPLATE_EVENTS_ACTION, payload: id})
        });
    }
}

export function deleteTemplateEvents(id, token) {
    return dispatch => {
        axios.delete(DELETE_TEMPLATE_EVENTS(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: DELETE_TEMPLATE_EVENTS_ACTION, payload: response.data})
        });
    }
}
