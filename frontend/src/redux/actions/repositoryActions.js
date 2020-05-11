import {
    CATEGORY_REPOSITORY_ACTION,
    EVENTS_REPOSITORY_ACTION,
    EXPAND_REPOSITORY_ACTION,
    MINIMALIZE_REPOSITORY_ACTION, TAGS_REPOSITORY_ACTION,
    TEMPLATES_REPOSITORY_ACTION
} from "../constants/ActionTypes";

export function expand() {
    return dispatch => {
        dispatch({type: EXPAND_REPOSITORY_ACTION})
    }
}

export function minimize() {
    return dispatch => {
        dispatch({type: MINIMALIZE_REPOSITORY_ACTION})
    }
}

export function templatesRepository() {
    return dispatch => {
        dispatch({type: TEMPLATES_REPOSITORY_ACTION})
    }
}

export function eventsRepository() {
    return dispatch => {
        dispatch({type: EVENTS_REPOSITORY_ACTION})
    }
}

export function tagsRepository() {
    return dispatch => {
        dispatch({type: TAGS_REPOSITORY_ACTION})
    }
}

export function categoriesRepository() {
    return dispatch => {
        dispatch({type: CATEGORY_REPOSITORY_ACTION})
    }
}

