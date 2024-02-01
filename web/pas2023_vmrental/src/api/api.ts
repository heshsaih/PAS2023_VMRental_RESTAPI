import { ApiResponseType } from "../types/ApiResponse";
import { CreateRentType, RentType } from "../types/Rent";
import { CreateUserType, ModifyUserType, UserType } from "../types/User";
import { VirtualDeviceType } from "../types/VirtualDevice";
import { apiWithConfig } from "./api.config";
import {SignInType} from "../types/SignIn.ts";

export const api = {
    getUserById: (userId: string): ApiResponseType<UserType> => {
        return apiWithConfig.get(`/users/${userId}`);
    },

    getAllUsers: async (): ApiResponseType<UserType[]> => {
        return apiWithConfig.get("/users").catch(error => error);
    },

    getUserByUsername: async (username: string): ApiResponseType<UserType> => {
        return apiWithConfig.get(`/users/getbyusername?username=${username}`);
    },

    createClient: async (newClient: CreateUserType): ApiResponseType<any> => {
        return apiWithConfig.post("/users/createclient", JSON.stringify(newClient)).catch(error => error);
    },

    createAdministrator: async (newAdministrator: CreateUserType): ApiResponseType<any> => {
        return apiWithConfig.post("/users/createadministrator", JSON.stringify(newAdministrator)).catch(error => error);
    },

    createResourceManager: async (newResourceManager: CreateUserType): ApiResponseType<any> => {
        return apiWithConfig.post("/users/createresourcemanager", JSON.stringify(newResourceManager)).catch(error => error);
    },

    getUsersRents: async (userId: string): ApiResponseType<RentType[]> => {
        return apiWithConfig.get(`/rents/getbyuserid/${userId}`);
    },

    setUserActive: async (userId: string): ApiResponseType<any> => {
        return apiWithConfig.patch(`/users/${userId}/activate`);
    },

    setUserInactive: async (userId: string): ApiResponseType<any> => {
        return apiWithConfig.patch(`/users/${userId}/deactivate`);
    },

    modifyUser: async (userId: string, newUser: ModifyUserType): ApiResponseType<any> => {
        return apiWithConfig.put(`/users/${userId}`, JSON.stringify(newUser)).catch(error => error);
    },

    updateUserType: async (userId: string, clientType: string) => {
        return apiWithConfig.patch(`/users/${userId}/updateclienttype?clientType=${clientType}`).catch(error => error);
    },

    getAllRents: async (): ApiResponseType<RentType[]> => {
        return apiWithConfig.get("/rents");
    },

    createRent: async (newRent: CreateRentType): ApiResponseType<any> => {
        return apiWithConfig.post("/rents", JSON.stringify(newRent)).catch(error => error);
    },

    deleteRent: async (rentId: string): ApiResponseType<any> => {
        return apiWithConfig.delete(`/rents/${rentId}`).catch(error => error);
    },

    getAllVirtualDevices: async (): ApiResponseType<VirtualDeviceType[]> => {
        return apiWithConfig.get("/virtual-devices");
    },

    getVirtualDeviceById: async (virtualDeviceId: string): ApiResponseType<VirtualDeviceType> => {
        return apiWithConfig.get(`/virtual-devices/${virtualDeviceId}`);
    },

    logIn: async (signInData: SignInType): ApiResponseType<any> => {
        return apiWithConfig.post("/users/signin", JSON.stringify(signInData));
    },

    logOut: async (): ApiResponseType<any> => {
        return apiWithConfig.post("/users/self/signout");
    },

    getCurrentUser: async (): ApiResponseType<UserType> => {
        return apiWithConfig.get("/users/self");
    },

    getCurrentUserRents: async (): ApiResponseType<RentType[]> => {
        return apiWithConfig.get("/rents/self/rents");
    },

    createCurrentUsersRent: async (newRent: CreateRentType): ApiResponseType<any> => {
        return apiWithConfig.post("/rents/self/rents", newRent).catch(error => error);
    },

    deleteCurrentUsersRent: async (rentId: string): ApiResponseType<any> => {
        return apiWithConfig.delete(`rents/self/rents/${rentId}`);
    },

    modifyCurrentUser: async (newUser: ModifyUserType) : ApiResponseType<any> => {
        return apiWithConfig.put("/users/self", newUser);
    }
};