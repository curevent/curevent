import axios from "axios";
import {GET_REFRESH, GET_WHO_AM_I, POST_AUTH, POST_REGISTER} from "../api/ApiEndpoints";
import {AUTH_ACTION, REGISTER_ACTION, WHO_AM_I_ACTION} from "./ActionTypes";

export function postAuth(authInfo) {
    return dispatch => {
        axios.post(POST_AUTH, authInfo).then(response => {
            window.localStorage.setItem("access_token", response.data.access_token);
            window.localStorage.setItem("refresh_token", response.data.refresh_token);
            dispatch({type: AUTH_ACTION, payload: response.data});
        });
    }
}

export function postRegister(registerInfo) {
    return dispatch => {
        axios.post(POST_REGISTER, registerInfo).then(response => {
            dispatch({type: REGISTER_ACTION, payload: response.data})
        });
    }
}

export function getWhoAmI(token) {
    return dispatch => {
        axios({
            method: 'get',
            url: GET_WHO_AM_I,
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }).then(response => {
            dispatch({type: WHO_AM_I_ACTION, payload: response.data})
        })
    }
}