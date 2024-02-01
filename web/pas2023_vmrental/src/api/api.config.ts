import axios from "axios";
import {api} from "./api.ts";

export const API_URL = "https://localhost:8080";
export const TIMEOUT_IN_MS = 30000;
export const DEFAULT_HEADERS = {
    Accept: "application/json",
    "Content-Type": "application/json"
};

export const apiWithConfig = axios.create({
    baseURL: API_URL,
    timeout: TIMEOUT_IN_MS,
    headers: DEFAULT_HEADERS
});

apiWithConfig.interceptors.request.use((config) => {
    const token = window.localStorage.getItem("token");
    if (token && config.headers) {
        config.headers.Authorization = "Bearer " + JSON.parse(token);
    }
    return config;
});

apiWithConfig.interceptors.request.use((config) => {
    const etag = window.localStorage.getItem("etag");
    if (etag && config.headers) {
        config.headers["If-Match"] = JSON.parse(etag);
    }
    return config;
});


apiWithConfig.interceptors.response.use(
    (response) => {
        console.log(response);
        return response;
    },
    (error) => {
        const status = error.response?.status;
        if (status === 401 || status === 404) {
            window.localStorage.removeItem("token");
        }
        return Promise.reject(error);
    }
);