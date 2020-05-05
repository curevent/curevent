import axios from "axios";
import {
    DELETE_USER_BY_ID,
    DELETE_USER_EVENTS,
    DELETE_USER_FRIENDS,
    DELETE_USER_TEMPLATES,
    FILTER_USER_EVENTS_BY_TAGS,
    FILTER_USER_EVENTS_IN_INTERVAL_BY_TAGS,
    FILTER_USER_FRIENDS_EVENTS_BY_TAGS,
    FILTER_USER_TEMPLATES_BY_TAGS,
    FIND_USER_BY_NAME_AND_SURNAME,
    FIND_USER_BY_USERNAME,
    GET_USER_BY_ID,
    GET_USER_EVENTS_IN_INTERVAL,
    GET_USER_FRIENDS,
    GET_USER_FRIENDS_EVENTS_IN_INTERVAL,
    GET_USERS,
    POST_USER,
    PUT_USER
} from "../constants/ApiEndpoints";
import {getTokens} from "../../utils/localStorageUtils";

export async function postUser(user) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.post(POST_USER, user, {headers: {Authorization: access}});
    return response.data;
}

export async function getUser(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.get(GET_USER_BY_ID(id),{headers: {Authorization: access}});
    return response.data;
}

export async function getUsers() {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.get(GET_USERS,{headers: {Authorization: access}});
    return response.data;
}


export async function getUserFriends(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.get(GET_USER_FRIENDS(id), {headers: {Authorization: access}});
    return response.data;
}

export async function getUserEventsInInterval(id, interval) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.get(GET_USER_EVENTS_IN_INTERVAL(id, interval), {headers: {Authorization: access}});
    return response.data;
}

export async function getUserFriendsEventsInInterval(id, interval) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.get(GET_USER_FRIENDS_EVENTS_IN_INTERVAL(id, interval), {headers: {Authorization: access}});
    return response.data;
}

export async function putUser(user) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.put(PUT_USER, user, {headers: {Authorization: access}});
    return response.data;
}

export async function deleteUser(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.delete(DELETE_USER_BY_ID(id), {headers: {Authorization: access}});
    return response.data;
}

export async function deleteUserEvents(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.delete(DELETE_USER_FRIENDS(id), {headers: {Authorization: access}});
    return response.data;
}

export async function deleteUserTemplates(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.delete(DELETE_USER_TEMPLATES(id), {headers: {Authorization: access}});
    return response.data;
}

export async function deleteUserFriends(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.delete(DELETE_USER_EVENTS(id), {headers: {Authorization: access}});
    return response.data;
}

export async function findUser(username, name, surname) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    if(username != null) {
        const response = await axios.get(FIND_USER_BY_USERNAME(username), {headers: {Authorization: access}});
        return response.data;
    } else {
        const response = await axios.get(FIND_USER_BY_NAME_AND_SURNAME(name, surname), {headers: {Authorization: access}});
        return response.data;
    }
}

export async function filterUserTemplates(userId, tagsId) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.get(FILTER_USER_TEMPLATES_BY_TAGS(id), {headers: {Authorization: access}});
    return response.data;
}

export async function filterUserEvents(userId, tagsId) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.get(FILTER_USER_EVENTS_BY_TAGS(userId, tagsId), {headers: {Authorization: access}});
    return response.data;
}

export async function filterUserEventsInInterval(userId, tagsId, interval) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.get(FILTER_USER_EVENTS_IN_INTERVAL_BY_TAGS(userId, tagsId, interval), {headers: {Authorization: access}});
    return response.data;
}

export async function filterUserFriendsEventsInInterval(userId, tagsId, interval, token) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.get(FILTER_USER_FRIENDS_EVENTS_BY_TAGS(userId, tagsId, interval), {headers: {Authorization: access}});
    return response.data;
}