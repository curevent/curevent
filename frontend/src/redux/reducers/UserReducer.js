import {
    DELETE_USER_ACTION,
    DELETE_USER_EVENTS_ACTION,
    DELETE_USER_FRIENDS_ACTION,
    DELETE_USER_TEMPLATES_ACTION,
    GET_USER_ACTION,
    GET_USER_EVENTS_IN_INTERVAL_ACTION,
    GET_USER_FRIENDS_ACTION,
    GET_USER_FRIENDS_EVENTS_IN_INTERVAL_ACTION, GET_USERS_ACTION,
    POST_USER_ACTION,
    PUT_USER_ACTION, SAVE_USER_ACTION
} from "../constants/ActionTypes";

const initialState = {
    allUsers: [],
    curUser:{
        id: null,
        username: null,
        email: null,
        name: null,
        surname: null,
        country: null,
        city: null,
        status: null,

        relationships: [],
        events: [],
        templates: [],
        comments: []
    },
    userFriends:[],
    userCurEvents:[],
    userFriendsCurEvents:[]
};

export function userReducer(state = initialState, action) {
    switch (action.type) {
        case GET_USERS_ACTION:
            return {...state, ...{allUsers: action.payload}};
        case SAVE_USER_ACTION:
            console.log(action);
            return {...state, ...{curUser: action.payload}};
        case GET_USER_ACTION:
            return {...state, ...{
                curUser: action.payload,
                userFriends: [],
                userCurEvents: [],
                userFriendsCurEvents: []
            }};
        case GET_USER_FRIENDS_ACTION:
            return {...state, ...{userFriends: action.payload}};
        case GET_USER_EVENTS_IN_INTERVAL_ACTION:
            return {...state, ...{userCurEvents: action.payload}};
        case GET_USER_FRIENDS_EVENTS_IN_INTERVAL_ACTION:
            return {...state, ...{userFriendsCurEvents: action.payload}};
        case POST_USER_ACTION:
            return {...state, ...{
                allUsers: state.allUsers.push(action.payload),
                curUser: action.payload,
                userFriends: [],
                userCurEvents: [],
                userFriendsCurEvents: []
            }};
        case PUT_USER_ACTION:
            return {...state, ...{curUser: action.payload}};
        case DELETE_USER_ACTION:
            return {...state, ...{
                allUsers: state.allUsers.filter(user => user.id !== action.payload),
                curUser: initialState.curUser,
                userFriends: [],
                userCurEvents: [],
                userFriendsCurEvents: []
            }};
        case DELETE_USER_TEMPLATES_ACTION:
            return {...state, ...{
                curUser: action.payload,
                userCurEvents: []
            }};
        case DELETE_USER_EVENTS_ACTION:
            return {...state, ...{
                curUser: action.payload,
                userCurEvents: []
            }};
        case DELETE_USER_FRIENDS_ACTION:
            return {...state, ...{
                curUser: action.payload,
                userFriends: [],
                userFriendsCurEvents: []
            }};
        default:
            return state;
    }
}
