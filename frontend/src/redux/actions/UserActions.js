import {
    DELETE_USER_ACTION,
    DELETE_USER_EVENTS_ACTION,
    DELETE_USER_FRIENDS_ACTION,
    DELETE_USER_TEMPLATES_ACTION,
    FILTER_USER_EVENTS_BY_TAGS_ACTION,
    FILTER_USER_EVENTS_IN_INTERVAL_BY_TAGS_ACTION,
    FILTER_USER_FRIENDS_EVENTS_BY_TAGS_ACTION,
    FILTER_USER_TEMPLATES_BY_TAGS_ACTION,
    FIND_USER_ACTION,
    GET_USER_ACTION,
    GET_USER_EVENTS_IN_INTERVAL_ACTION,
    GET_USER_FRIENDS_ACTION,
    GET_USER_FRIENDS_EVENTS_IN_INTERVAL_ACTION,
    GET_USERS_ACTION,
    POST_USER_ACTION,
    PUT_USER_ACTION, SAVE_USER_ACTION
} from "../constants/ActionTypes";

export function postUser(user) {
    return dispatch => {
        dispatch({type: POST_USER_ACTION, payload: user});
    }
}

export function saveUser(user) {
    return dispatch => {
        dispatch({type: SAVE_USER_ACTION, payload: user});
    }
}

export function getUsers() {
    return dispatch => {
        dispatch({type: GET_USERS_ACTION, payload: null});
    }
}


export function getUserFriends(id, token) {
    return dispatch => {
        dispatch({type: GET_USER_FRIENDS_ACTION, payload: null});
    }
}

export function getUserEventsInInterval(id, interval) {
    return dispatch => {
        dispatch({type: GET_USER_EVENTS_IN_INTERVAL_ACTION, payload: null});
    }
}

export function getUserFriendsEventsInInterval(id, interval) {
    return dispatch => {
        dispatch({type: GET_USER_FRIENDS_EVENTS_IN_INTERVAL_ACTION, payload: null});
    }
}

export function putUser(user) {
    return dispatch => {
        dispatch({type: PUT_USER_ACTION, payload: user});
    }
}

export function deleteUser(id) {
    return dispatch => {
        dispatch({type: DELETE_USER_ACTION, payload: id});
    }
}

export function deleteUserEvents(id) {
    return dispatch => {
        dispatch({type: DELETE_USER_EVENTS_ACTION, payload: id});
    }
}

export function deleteUserTemplates(id) {
    return dispatch => {
        dispatch({type: DELETE_USER_TEMPLATES_ACTION, payload: id});
    }
}

export function deleteUserFriends(id) {
    return dispatch => {
        dispatch({type: DELETE_USER_FRIENDS_ACTION, payload: id});
    }
}

export function findUser(username, name, surname) {
    return dispatch => {
        dispatch({type: FIND_USER_ACTION, payload: {username:username, name:name, surname:surname}});
    }
}

export function filterUserTemplates(userId, tagsId) {
    return dispatch => {
        dispatch({type: FILTER_USER_TEMPLATES_BY_TAGS_ACTION, payload: {userId:userId, tagsId:tagsId}});
    }
}

export function filterUserEvents(userId, tagsId) {
    return dispatch => {
        dispatch({type: FILTER_USER_EVENTS_BY_TAGS_ACTION, payload: {userId:userId, tagsId:tagsId}});
    }
}

export function filterUserEventsInInterval(userId, tagsId, interval) {
    return dispatch => {
        dispatch({type: FILTER_USER_EVENTS_IN_INTERVAL_BY_TAGS_ACTION, payload: {userId:userId, tagsId:tagsId, interval:interval}});
    }
}

export function filterUserFriendsEventsInInterval(userId, tagsId, interval) {
    return dispatch => {
        dispatch({type: FILTER_USER_FRIENDS_EVENTS_BY_TAGS_ACTION, payload: {userId:userId, tagsId:tagsId, interval:interval}});
    }
}
