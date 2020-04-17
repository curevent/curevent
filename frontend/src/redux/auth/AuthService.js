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
    const access = `Bearer ${tokens.access}`;
    const response = await axios.get(GET_WHO_AM_I, {headers: {Authorization: access}});
    return response.data;
}

export async function getRefresh() {
    const local_tokens = getTokens();
    if (local_tokens.refresh !== undefined) {
        const url = GET_REFRESH(local_tokens.refresh);
        const access = `Bearer ${local_tokens.access}`;
        const response = await axios.get(url, {headers: {Authorization: access}});
        const new_tokens = response.data;
        saveTokens(new_tokens);
        return new_tokens;
    }
}
