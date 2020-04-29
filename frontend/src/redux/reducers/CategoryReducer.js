import {
    DELETE_CATEGORY_ACTION,
    GET_CATEGORY_ACTION,
    POST_CATEGORY_ACTION,
    PUT_CATEGORY_ACTION
} from "../constants/ActionTypes";

const initialState = {
    categoryInfo: {
        id: null,
        description: null
    }
};

export function categoryReducer(state = initialState, action) {
    switch (action.type) {
        case GET_CATEGORY_ACTION:
            return {...state, ...{categoryInfo: action.payload}};
        case POST_CATEGORY_ACTION:
            return {...state, ...{categoryInfo: action.payload}};
        case PUT_CATEGORY_ACTION:
            return {...state, ...{categoryInfo: action.payload}};
        case DELETE_CATEGORY_ACTION:
            return {...state, ...initialState};
        default:
            return state;
    }
}
