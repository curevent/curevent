import axios from "axios";
import {POST_AUTH, POST_REGISTER} from "../api/ApiEndpoints";
import {AUTH_ACTION, REGISTER_ACTION} from "./ActionTypes";

export function postAuth(authInfo) {
    return dispatch => {
        axios.post(POST_AUTH, authInfo).then(response => {
            dispatch({type: AUTH_ACTION, payload: response.data})
        });
    }
}

export function postRegister(registerInfo) {
    return async dispatch => {
        axios.post(POST_REGISTER, registerInfo).then(response => {
            dispatch({type: REGISTER_ACTION, payload: response.data})
        });
    }
}