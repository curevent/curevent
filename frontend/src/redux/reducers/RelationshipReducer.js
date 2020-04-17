import {
    DELETE_RELATIONSHIP_ACTION,
    GET_RELATIONSHIP_ACTION,
    POST_RELATIONSHIP_ACTION,
    PUT_RELATIONSHIP_ACTION,
} from "../constants/ActionTypes";

const initialState = {
    relationships:[]
};

export function relationshipReducer(state = initialState, action) {
    switch (action.type) {
        case GET_RELATIONSHIP_ACTION:
            return state.relationships.map(relationship => {
                return relationship.id !== action.payload.id ? relationship : action.payload});
        case POST_RELATIONSHIP_ACTION:
            return {...state, relationships: [...state.relationships, action.payload]};
        case PUT_RELATIONSHIP_ACTION:
            return state.relationships.map(relationship => {
                return relationship.id !== action.payload.id ? relationship : action.payload});
        case DELETE_RELATIONSHIP_ACTION:
            return state.relationships.filter(relationship => relationship.id !== action.payload);
        default:
            return state;
    }
}
