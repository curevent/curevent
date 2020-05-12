import {
    CATEGORY_REPOSITORY_ACTION, EVENTS_REPOSITORY_ACTION,
    EXPAND_REPOSITORY_ACTION,
    MINIMALIZE_REPOSITORY_ACTION, TAGS_REPOSITORY_ACTION, TEMPLATES_REPOSITORY_ACTION,
} from "../constants/ActionTypes";

const initialState = {
    isMinimized: true,
    content: "templates"
};

export function repositoryReducer(state = initialState, action) {
    switch (action.type) {
        case MINIMALIZE_REPOSITORY_ACTION:
            console.log(action);
            return {...state, isMinimized: true};
        case EXPAND_REPOSITORY_ACTION:
            console.log(action);
            return {...state, isMinimized: false};
        case CATEGORY_REPOSITORY_ACTION:
            console.log(action);
            return {...state, content: "categories"};
        case TEMPLATES_REPOSITORY_ACTION:
            console.log(action);
            return {...state, content: "templates"};
        case EVENTS_REPOSITORY_ACTION:
            console.log(action);
            return {...state, content: "events"};
        case TAGS_REPOSITORY_ACTION:
            console.log(action);
            return {...state, content: "tags"};
        default:
            return state;
    }
}
