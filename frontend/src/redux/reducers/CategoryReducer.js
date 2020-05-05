import {
    DELETE_CATEGORY_ACTION,
    GET_CATEGORY_ACTION,
    POST_CATEGORY_ACTION,
    PUT_CATEGORY_ACTION
} from "../constants/ActionTypes";

const initialState = {
    categories:[]
};

export function categoryReducer(state = initialState, action) {
    switch (action.type) {
        case GET_CATEGORY_ACTION:
            return state.categories.map(category => {
                return category.id !== action.payload.id ? category : action.payload});
        case POST_CATEGORY_ACTION:
            return {...state, categories: [...state.categories, action.payload]};
        case PUT_CATEGORY_ACTION:
            return state.categories.map(category => {
                return category.id !== action.payload.id ? category : action.payload});
        case DELETE_CATEGORY_ACTION:
            return state.categories.filter(category => category.id !== action.payload);
        default:
            return state;
    }
}
