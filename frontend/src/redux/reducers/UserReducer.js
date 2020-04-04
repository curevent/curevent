import {
    DELETE_USER_ACTION,
    DELETE_USER_EVENTS_ACTION,
    DELETE_USER_FRIENDS_ACTION,
    DELETE_USER_TEMPLATES_ACTION,
    GET_USER_ACTION,
    GET_USER_EVENTS_IN_INTERVAL_ACTION,
    GET_USER_FRIENDS_ACTION,
    GET_USER_FRIENDS_EVENTS_IN_INTERVAL_ACTION,
    POST_USER_ACTION,
    PUT_USER_ACTION
} from "../actions/ActionTypes";

const initialState = {
    users:[],
    curUser:{},
    userFriends:[],
    userEvents:[],
    userFriendsEvents:[]
};

export function userReducer(state = initialState, action) {
    switch (action.type) {
        case GET_USER_ACTION:
            return {...state,
                curUser: action.payload,
                userFriends: [],
                userEvents: [],
                userFriendsEvents: []
            };
        case GET_USER_FRIENDS_ACTION:
            return {...state,
                userFriends: action.payload,
            };
        case GET_USER_EVENTS_IN_INTERVAL_ACTION:
            return {...state,
                userEvents: []
            };
        case GET_USER_FRIENDS_EVENTS_IN_INTERVAL_ACTION:
            return {...state,
                userFriendsEvents: []
            };
        case POST_USER_ACTION:
            return {...state,
                users: [...state.templates, action.payload],
                curUser: action.payload,
                userFriends: [],
                userEvents: [],
                userFriendsEvents: []
            };
        case PUT_USER_ACTION:
            return state.users.map(user => {
                return user.id !== action.payload.id ? user : action.payload
            });
        case DELETE_USER_ACTION:
            return {
                users: state.users.filter(user => user.id !== action.payload),
                curUser: null,
                userFriends: [],
                userEvents: [],
                userFriendsEvents: []
            };
        case DELETE_USER_TEMPLATES_ACTION:
            return {...state,
                curUser: action.payload
            };
        case DELETE_USER_EVENTS_ACTION:
            return {...state,
                curUser: action.payload
            };
        case DELETE_USER_FRIENDS_ACTION:
            return {...state,
                curUser: action.payload
            };
        default:
            return state;
    }
}