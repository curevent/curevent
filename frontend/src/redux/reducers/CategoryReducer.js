import {GET_CATEGORY_ACTION, POST_CATEGORY_ACTION, PUT_CATEGORY_ACTION, DELETE_CATEGORY_ACTION} from "../actions/ActionTypes";

const initialState = {
    category:{}
};

export function categoryReducer(state = initialState, action) {
    switch (action.type) {
        case GET_CATEGORY_ACTION:
            return {...state, category: action.payload};
        case POST_CATEGORY_ACTION:
            return {...state, category: action.payload};
        case PUT_CATEGORY_ACTION:
            return {...state, category: action.payload};
        case DELETE_CATEGORY_ACTION:
            return {...state, category: action.payload};
        default:
            return state;
    }
}