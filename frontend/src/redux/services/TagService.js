import axios from "axios";
import {DELETE_TAG_BY_ID, GET_TAG_BY_ID, POST_TAG, PUT_TAG} from "../constants/ApiEndpoints";
import {getTokens} from "../../utils/localStorageUtils";


export async function postTag(tag) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.post(POST_TAG, tag, {headers: {Authorization: access}});
    return response.data;
}

export async function getTag(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.post(GET_TAG_BY_ID(id), {headers: {Authorization: access}});
    return response.data;
}

export async function putTag(tag) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.put(PUT_TAG, tag, {headers: {Authorization: access}});
    return response.data;
}

export async function deleteTag(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.delete(DELETE_TAG_BY_ID(id), {headers: {Authorization: access}});
    return response.data;
}