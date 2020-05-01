const BASE_ADDRESS = `http://localhost:`;
const PORT = `8080`;
const API_VERSION = `/api/v1`;

export const BASE_ENDPOINT = BASE_ADDRESS + PORT + API_VERSION;
export const USER_ENDPOINT = BASE_ENDPOINT + `/users`;
export const EVENT_ENDPOINT = BASE_ENDPOINT + `/events`;
export const TEMPLATE_ENDPOINT = BASE_ENDPOINT + `/templates`;
export const TAG_ENDPOINT = BASE_ENDPOINT + `/tags`;
export const CATEGORY_ENDPOINT = BASE_ENDPOINT + `/categories`;
export const COMMENT_ENDPOINT = BASE_ENDPOINT + `/comments`;
export const RELATIONSHIP_ENDPOINT = BASE_ENDPOINT + `/relationships`;


export const POST_AUTH = `${BASE_ENDPOINT}/login`;
export const POST_REGISTER = `${BASE_ENDPOINT}/register`;
export const GET_WHO_AM_I = `${BASE_ENDPOINT}/whoami`;
export const GET_REFRESH = (refresh) => `${BASE_ENDPOINT}/refresh?refresh_token=${refresh}`;

// users
export const POST_USER = `${USER_ENDPOINT}/`;

export const GET_USERS = `${USER_ENDPOINT}/`;
export const GET_USER_BY_ID = (id) => `${USER_ENDPOINT}/${id}`;
export const GET_USER_FRIENDS = (id) => `${USER_ENDPOINT}/${id}/friends`;
export const GET_USER_EVENTS_IN_INTERVAL = (id) => `${USER_ENDPOINT}/${id}/events`;
export const GET_USER_FRIENDS_EVENTS_IN_INTERVAL = (id) => `${USER_ENDPOINT}/${id}/friends/events`;

export const PUT_USER = `${USER_ENDPOINT}/`;

export const DELETE_USER_BY_ID = (id) => `${USER_ENDPOINT}/${id}`;
export const DELETE_USER_FRIENDS = (id) => `${USER_ENDPOINT}/${id}/friends`;
export const DELETE_USER_EVENTS = (id) => `${USER_ENDPOINT}/${id}/events`;
export const DELETE_USER_TEMPLATES = (id) => `${USER_ENDPOINT}/${id}/templates`;

export const FILTER_USER_TEMPLATES_BY_TAGS = (id, tagsId) => `${USER_ENDPOINT}/${id}/templates/search?tagsId=${tagsId}`;
export const FILTER_USER_EVENTS_BY_TAGS = (id, tagsId) => `${USER_ENDPOINT}/${id}/events/search?tagsId=${tagsId}`;
export const FILTER_USER_EVENTS_IN_INTERVAL_BY_TAGS = (id, tagsId, interval) => `${USER_ENDPOINT}/${id}/events/search?tagsId=${tagsId}&interval=${interval}`;
export const FILTER_USER_FRIENDS_EVENTS_BY_TAGS = (id, tagsId, interval) => `${USER_ENDPOINT}/${id}/friends/events/search?tagsId=${tagsId}&interval=${interval}`;
export const FIND_USER_BY_USERNAME = (username) => `${USER_ENDPOINT}/search?username=${username}`;
export const FIND_USER_BY_NAME_AND_SURNAME = (name, surname) => `${USER_ENDPOINT}/search?name=${name}&surname=${surname}`;

// events
export const POST_EVENT = `${EVENT_ENDPOINT}/`;
export const GET_EVENT_BY_ID = (id) => `${EVENT_ENDPOINT}/${id}`;
export const PUT_EVENT = `${EVENT_ENDPOINT}/`;
export const DELETE_EVENT_BY_ID = (id) => `${EVENT_ENDPOINT}/${id}`;

// templates
export const POST_TEMPLATE = `${TEMPLATE_ENDPOINT}/`;
export const POST_REPEAT_FORM_AND_CREATE_TEMPLATE_EVENTS = (id) =>  `${TEMPLATE_ENDPOINT}/${id}/events`;

export const GET_TEMPLATE_BY_ID = (id) => `${TEMPLATE_ENDPOINT}/${id}`;

export const PUT_TEMPLATE_AND_TEMPLATE_EVENTS = `${TEMPLATE_ENDPOINT}/`;

export const DELETE_TEMPLATE_AND_TEMPLATE_EVENTS = (id) => `${TEMPLATE_ENDPOINT}/${id}`;
export const DELETE_TEMPLATE_EVENTS = (id) => `${TEMPLATE_ENDPOINT}/${id}/events`;

//tags
export const POST_TAG = `${TAG_ENDPOINT}/`;
export const GET_TAG_BY_ID = (id) => `${TAG_ENDPOINT}/${id}`;
export const PUT_TAG = `${TAG_ENDPOINT}/`;
export const DELETE_TAG_BY_ID = (id) => `${TAG_ENDPOINT}/${id}`;

//comments
export const POST_COMMENT = `${COMMENT_ENDPOINT}/`;
export const GET_COMMENT_BY_ID = (id) => `${COMMENT_ENDPOINT}/${id}`;
export const PUT_COMMENT = `${COMMENT_ENDPOINT}/`;
export const DELETE_COMMENT_BY_ID = (id) => `${COMMENT_ENDPOINT}/${id}`;

//categories
export const POST_CATEGORY = `${CATEGORY_ENDPOINT}/`;
export const GET_CATEGORY_BY_ID = (id) => `${CATEGORY_ENDPOINT}/${id}`;
export const PUT_CATEGORY = `${CATEGORY_ENDPOINT}/`;
export const DELETE_CATEGORY_BY_ID = (id) => `${CATEGORY_ENDPOINT}/${id}`;

//relationships
export const POST_RELATIONSHIP = `${RELATIONSHIP_ENDPOINT}/`;
export const GET_RELATIONSHIP_BY_ID = (id) => `${RELATIONSHIP_ENDPOINT}/${id}`;
export const PUT_RELATIONSHIP= `${RELATIONSHIP_ENDPOINT}/`;
export const DELETE_RELATIONSHIP_BY_ID = (id) => `${RELATIONSHIP_ENDPOINT}/${id}`;
