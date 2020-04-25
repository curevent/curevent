import axios from "axios";
import {PUT_USER} from "../constants/ApiEndpoints";
import {getTokens} from "../../utils/localStorageUtils";

export async function putUser(user) {
    const tokens = getTokens();
    const access = `Bearer ${tokens.access}`;
    const response = await axios.put(PUT_USER, user, {headers: {Authorization: access}});
    return response.data;
}
