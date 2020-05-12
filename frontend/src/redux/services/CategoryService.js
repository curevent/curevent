import axios from "axios";
import {DELETE_CATEGORY_BY_ID, GET_CATEGORY_BY_ID, POST_CATEGORY, PUT_CATEGORY} from "../constants/ApiEndpoints";
import {getTokens} from "../../utils/localStorageUtils";


export async function postCategory(category) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.post(POST_CATEGORY, category, {headers: {Authorization: access}});
    return response.data;
}

export async function getCategory(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.post(GET_CATEGORY_BY_ID(id), {headers: {Authorization: access}});
    return response.data;
}

export async function putCategory(category) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.put(PUT_CATEGORY, category, {headers: {Authorization: access}});
    return response.data;
}

export async function deleteCategory(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.delete(DELETE_CATEGORY_BY_ID(id), {headers: {Authorization: access}});
    return response.data;
}