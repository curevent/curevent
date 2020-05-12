import {TIMELINE_SET_DATE_ACTION} from "../../constants/ActionTypes";

let date = new Date();

const initialState = {
    start: {
        year: date.getFullYear(),
        month: date.getMonth() + 1,
        day: date.getDate(),
        hour: 0,
        minute: 0,
    },
    end: {
        year: date.getFullYear(),
        month: date.getMonth() + 1,
        day: date.getDate() + 1,
        hour: 0,
        minute: 0,
    }
};

export function timelineReducer(state = initialState, action) {
    switch (action.type) {
        case TIMELINE_SET_DATE_ACTION:
            console.log(action);
            return {...state, ...{start: action.payload.start}, ...{end: action.payload.end}};
        default:
            return state;
    }
}
