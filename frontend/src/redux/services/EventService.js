import axios from "axios";
import {DELETE_EVENT_BY_ID, GET_EVENT_BY_ID, POST_EVENT, PUT_EVENT} from "../constants/ApiEndpoints";
import {getTokens} from "../../utils/localStorageUtils";


export async function postEvent(event) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.post(POST_EVENT, event, {headers: {Authorization: access}});
    return response.data;
}

export async function getEvent(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.post(GET_EVENT_BY_ID(id), {headers: {Authorization: access}});
    return response.data;
}

export async function putEvent(event) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.put(PUT_EVENT, event, {headers: {Authorization: access}});
    return response.data;
}

export async function deleteEvent(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.delete(DELETE_EVENT_BY_ID(id), {headers: {Authorization: access}});
    return response.data;
}