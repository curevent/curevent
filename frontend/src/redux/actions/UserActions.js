import axios from "axios";
import {
    DELETE_USER_BY_ID, DELETE_USER_EVENTS, DELETE_USER_FRIENDS, DELETE_USER_TEMPLATES,
    GET_USER_BY_ID,
    GET_USER_EVENTS_IN_INTERVAL,
    GET_USER_FRIENDS, GET_USER_FRIENDS_EVENTS_IN_INTERVAL,
    GET_USERS,
    POST_USER,
    PUT_USER
} from "../api/ApiEndpoints";
import {
    DELETE_USER_ACTION, DELETE_USER_EVENTS_ACTION, DELETE_USER_FRIENDS_ACTION, DELETE_USER_TEMPLATES_ACTION,
    GET_USER_ACTION, GET_USER_EVENTS_IN_INTERVAL_ACTION,
    GET_USER_FRIENDS_ACTION, GET_USER_FRIENDS_EVENTS_IN_INTERVAL_ACTION,
    GET_USERS_ACTION,
    POST_USER_ACTION,
    PUT_USER_ACTION
} from "./ActionTypes";

export function postUser(user, token) {
    return dispatch => {
        axios.post(POST_USER, user, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: POST_USER_ACTION, payload: response.data})
        });
    }
}

export function getUser(id, token) {
    return dispatch => {
        axios.get(GET_USER_BY_ID(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: GET_USER_ACTION, payload: response.data})
        });
    }
}

export function getUsers(token) {
    return dispatch => {
        axios.get(GET_USERS, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: GET_USERS_ACTION, payload: response.data})
        });
    }
}

export function getUserFriends(id, token) {
    return dispatch => {
        axios.get(GET_USER_FRIENDS(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: GET_USER_FRIENDS_ACTION, payload: response.data})
        });
    }
}

export function getUserEventsInInterval(id, token) {
    return dispatch => {
        axios.get(GET_USER_EVENTS_IN_INTERVAL(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: GET_USER_EVENTS_IN_INTERVAL_ACTION, payload: response.data})
        });
    }
}

export function getUserFriendsEventsInInterval(id, token) {
    return dispatch => {
        axios.get(GET_USER_FRIENDS_EVENTS_IN_INTERVAL(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: GET_USER_FRIENDS_EVENTS_IN_INTERVAL_ACTION, payload: response.data})
        });
    }
}

export function putUser(user, token) {
    return dispatch => {
        axios.put(PUT_USER, user, {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: PUT_USER_ACTION, payload: response.data})
        });
    }
}

export function deleteUser(id, token) {
    return dispatch => {
        axios.delete(DELETE_USER_BY_ID(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: DELETE_USER_ACTION, payload: id})
        });
    }
}

export function deleteUserEvents(id, token) {
    return dispatch => {
        axios.delete(DELETE_USER_EVENTS(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: DELETE_USER_EVENTS_ACTION, payload: id})
        });
    }
}

export function deleteUserTemplates(id, token) {
    return dispatch => {
        axios.delete(DELETE_USER_TEMPLATES(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: DELETE_USER_TEMPLATES_ACTION, payload: id})
        });
    }
}

export function deleteUserFriends(id, token) {
    return dispatch => {
        axios.delete(DELETE_USER_FRIENDS(id), {
            headers: {'Authorization':`Bearer ${token}`}
        }).then(response => {
            dispatch({type: DELETE_USER_FRIENDS_ACTION, payload: id})
        });
    }
}