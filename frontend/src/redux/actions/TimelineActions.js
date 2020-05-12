import {GET_AUTH_ACTION, TIMELINE_SET_DATE_ACTION} from "../constants/ActionTypes";

export function setDate(start, end) {
    return dispatch => {
        dispatch({type: TIMELINE_SET_DATE_ACTION, payload: {start, end}});
    }
}
