import axios from "axios";

export const API_URL = "http://localhost:8080";
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
        config.headers.Authorization = JSON.parse(token);
    }
    return config;
});

apiWithConfig.interceptors.response.use(
    (response) => response,
    (error) => Promise.reject(error)
);