import axios from "axios";
import {
    DELETE_TEMPLATE_AND_TEMPLATE_EVENTS,
    DELETE_TEMPLATE_EVENTS,
    GET_TEMPLATE_BY_ID,
    POST_REPEAT_FORM_AND_CREATE_TEMPLATE_EVENTS,
    POST_TEMPLATE,
    PUT_TEMPLATE_AND_TEMPLATE_EVENTS
} from "../constants/ApiEndpoints";
import {getTokens} from "../../utils/localStorageUtils";


export async function postTemplate(template) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.post(POST_TEMPLATE, template, {headers: {Authorization: access}});
    return response.data;
}

export async function createTemplateEvents(repeatForm, id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.post(POST_REPEAT_FORM_AND_CREATE_TEMPLATE_EVENTS(id), repeatForm, {headers: {Authorization: access}});
    return response.data;
}

export async function getTemplate(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.post(GET_TEMPLATE_BY_ID(id), {headers: {Authorization: access}});
    return response.data;
}

export async function putTemplate(template) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.put(PUT_TEMPLATE_AND_TEMPLATE_EVENTS, template, {headers: {Authorization: access}});
    return response.data;
}

export async function deleteTemplate(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.delete(DELETE_TEMPLATE_AND_TEMPLATE_EVENTS(id), {headers: {Authorization: access}});
    return response.data;
}

export async function deleteTemplateEvents(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.delete(DELETE_TEMPLATE_EVENTS(id), {headers: {Authorization: access}});
    return response.data;
}