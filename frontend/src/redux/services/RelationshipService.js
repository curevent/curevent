import axios from "axios";
import {
    DELETE_RELATIONSHIP_BY_ID,
    GET_RELATIONSHIP_BY_ID,
    POST_RELATIONSHIP,
    PUT_RELATIONSHIP
} from "../constants/ApiEndpoints";
import {getTokens} from "../../utils/localStorageUtils";


export async function postRelationship(relationship) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.post(POST_RELATIONSHIP, relationship, {headers: {Authorization: access}});
    return response.data;
}

export async function getRelationship(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.post(GET_RELATIONSHIP_BY_ID(id), {headers: {Authorization: access}});
    return response.data;
}

export async function putRelationship(relationship) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.put(PUT_RELATIONSHIP, relationship, {headers: {Authorization: access}});
    return response.data;
}

export async function deleteRelationship(id) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.delete(DELETE_RELATIONSHIP_BY_ID(id), {headers: {Authorization: access}});
    return response.data;
}