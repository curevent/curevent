import axios from "axios";
import {GET_REFRESH, GET_WHO_AM_I, POST_AUTH, POST_REGISTER} from "../constants/ApiEndpoints";
import {getTokens, saveTokens} from "../../utils/localStorageUtils";

export async function postAuth(authInfo) {
    const response = await axios.post(POST_AUTH, authInfo);
    const tokens = response.data;
    saveTokens(tokens);
    return tokens;
}

export async function postRegister(registerInfo) {
    const response = await axios.post(POST_REGISTER, registerInfo);
    const tokens = response.data;
    saveTokens(tokens);
    return tokens;
}

export async function getWhoAmI() {
    const tokens = getTokens();
    const access = `Authorization': Bearer ${tokens.access}`;
    const response = await axios.get(GET_WHO_AM_I, {headers: {access}});
    return response.data;
}

export async function getRefresh() {
    const local_tokens = getTokens();
    const url = GET_REFRESH(local_tokens.refresh);
    const access = `Authorization': Bearer ${local_tokens.access}`;
    const response = await axios.get(url, {headers: {access}});
    const new_tokens = response.data;
    saveTokens(new_tokens);
    return new_tokens;
}
