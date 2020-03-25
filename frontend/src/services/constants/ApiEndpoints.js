const LOCALHOST = `http://localhost:`;
const PORT = `3000`;
const API_VERSION = `/api/v1`;

export const BASE_ENDPOINT = LOCALHOST + PORT + API_VERSION;

export const GET_AUTH = `${BASE_ENDPOINT}/login`;
export const GET_REGISTER = `${BASE_ENDPOINT}/register`;
export const WHO_AM_I = `${BASE_ENDPOINT}/whoami`;
export const REFRESH = `${BASE_ENDPOINT}/refresh`;