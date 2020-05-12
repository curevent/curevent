import {
    DELETE_RELATIONSHIP_ACTION,
    GET_RELATIONSHIP_ACTION,
    POST_RELATIONSHIP_ACTION,
    PUT_RELATIONSHIP_ACTION,
} from "../constants/ActionTypes";

const initialState = {
    relationshipInfo: {
        id: null,
        ownerId: null,
        friendId: null,
        category: null
    }
};

export function relationshipReducer(state = initialState, action) {
    switch (action.type) {
        case GET_RELATIONSHIP_ACTION:
            return {...state, ...{relationshipInfo: action.payload}};
        case POST_RELATIONSHIP_ACTION:
            return {...state, ...{relationshipInfo: action.payload}};
        case PUT_RELATIONSHIP_ACTION:
            return {...state, ...{relationshipInfo: action.payload}};
        case DELETE_RELATIONSHIP_ACTION:
            return {...state, ...initialState};
        default:
            return state;
    }
}
