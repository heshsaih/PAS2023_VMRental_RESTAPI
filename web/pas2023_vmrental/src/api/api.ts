import { ApiResponseType, ApiResponseType, ApiResponseType, ApiResponseType } from "../types/ApiResponse";
import { CreateRentType, RentType } from "../types/Rent";
import { CreateUserType, ModifyUserType, UserType } from "../types/User";
import { VirtualDeviceType } from "../types/VirtualDevice";
import { apiWithConfig } from "./api.config";

export const api = {
    getUserById: (userId: string): ApiResponseType<UserType> => {
        return apiWithConfig.get(`/users/${userId}`);
    },

    getAllUsers: (): ApiResponseType<UserType[]> => {
        return apiWithConfig.get("/users").catch(error => error);
    },

    getUserByUsername: (username: string): ApiResponseType<UserType> => {
        return apiWithConfig.get(`/users/getbyusername?username=${username}`);
    },

    createClient: (newClient: CreateUserType): ApiResponseType<any> => {
        return apiWithConfig.post("/users/createclient", JSON.stringify(newClient)).catch(error => error);
    },

    createAdministrator: (newAdministrator: CreateUserType): ApiResponseType<any> => {
        return apiWithConfig.post("/users/createadministrator", JSON.stringify(newAdministrator)).catch(error => error);
    },

    createResourceManager: (newResourceManager: CreateUserType): ApiResponseType<any> => {
        return apiWithConfig.post("/users/createresourcemanager", JSON.stringify(newResourceManager)).catch(error => error);
    },

    getUsersRents: (userId: string): ApiResponseType<RentType[]> => {
        return apiWithConfig.get(`/rents/getbyuserid/${userId}`);
    },

    setUserActive: (userId: string): ApiResponseType<any> => {
        return apiWithConfig.patch(`/users/${userId}/activate`);
    },

    setUserInactive: (userId: string): ApiResponseType<any> => {
        return apiWithConfig.patch(`/users/${userId}/deactivate`);
    },

    modifyUser: (userId: string, newUser: ModifyUserType): ApiResponseType<any> => {
        return apiWithConfig.put(`/users/${userId}`, JSON.stringify(newUser)).catch(error => error);
    },

    getAllRents: (): ApiResponseType<RentType[]> => {
        return apiWithConfig.get("/rents");
    },

    createRent: (newRent: CreateRentType): ApiResponseType<any> => {
        return apiWithConfig.post("/rents", JSON.stringify(newRent)).catch(error => error);
    },

    deleteRent: (rentId: string): ApiResponseType<any> => {
        return apiWithConfig.delete(`/rents/${rentId}`).catch(error => error);
    },

    getAllVirtualDevices: (): ApiResponseType<VirtualDeviceType[]> => {
        return apiWithConfig.get("/virtual-devices");
    },

    getVirtualDeviceById: (virtualDeviceId: string): ApiResponseType<VirtualDeviceType> => {
        return apiWithConfig.get(`/virtual-devices/${virtualDeviceId}`);
    },

    logIn: (username: string, password: string) => {
        return username + password;
    },

    logOut: () => {

    },

    getCurrentUser: () => {
        
    }
};