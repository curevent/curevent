import axios from "axios";
import {DELETE_COMMENT_BY_ID, GET_COMMENT_BY_ID, POST_COMMENT, PUT_COMMENT} from "../constants/ApiEndpoints";

import {getTokens} from "../../utils/localStorageUtils";


export async function postComment(comment) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.post(POST_COMMENT, comment, {headers: {Authorization: access}});
    return response.data;
}

export async function getComment(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.post(GET_COMMENT_BY_ID(id), {headers: {Authorization: access}});
    return response.data;
}

export async function putComment(comment) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.put(PUT_COMMENT, comment, {headers: {Authorization: access}});
    return response.data;
}

export async function deleteComment(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.delete(DELETE_COMMENT_BY_ID(id), {headers: {Authorization: access}});
    return response.data;
}