
export const saveTokens = (data) => {
    window.localStorage.setItem("access_token", data.access_token);
    window.localStorage.setItem("refresh_token", data.refresh_token);
};

export const refreshToken = (data) => {
    window.localStorage.setItem("refresh_token", data.refresh_token);
};

export const invalidateLocalStorage = () => {
    console.log("Clear localStorage");
    window.localStorage.clear();
};

export const getTokens = () => {
    const access = window.localStorage.getItem("access_token");
    const refresh = window.localStorage.getItem("refresh_token");
    return {access, refresh}
};
