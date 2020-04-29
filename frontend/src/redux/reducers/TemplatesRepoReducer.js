import {
    EXPAND_REPOSITORY_ACTION,
    MINIMALIZE_REPOSITORY_ACTION,
} from "../constants/ActionTypes";

const initialState = {
    isMinimized: true
};

export function templatesRepoReducer(state = initialState, action) {
    switch (action.type) {
        case MINIMALIZE_REPOSITORY_ACTION:
            console.log(action);
            return {isMinimized: true};
        case EXPAND_REPOSITORY_ACTION:
            console.log(action);
            return {isMinimized: false};
        default:
            return state;
    }
}
