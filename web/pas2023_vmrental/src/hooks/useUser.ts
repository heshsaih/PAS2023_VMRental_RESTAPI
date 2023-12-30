import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useUserState } from "../context/UserContext";
import { UserTypeEnum } from "../enums/UserType.enum";
import { api } from "../api/api";
import { Pathnames } from "../router/pathnames";

export const useUser = () => {
    const navigate = useNavigate();
    const { user, setUser, isLoggingIn, setIsLoggingIn, isFetching, setIsFetching } = useUserState();

    const isAuthenticated = user?.username;
    const isAdmin = user?.userType === UserTypeEnum.ADMINISTRATOR;

    const logOut = async () => {
        try {
            setIsFetching(true);
            await api.logOut();
        } catch {
            alert("Logout failed");
        } finally {
            localStorage.removeItem("token");
            setUser(null);
            navigate(Pathnames.public.login);
            setIsFetching(false);
        }
    } 

    const logIn = async (username: string, password: string) => {
        try {
            setIsLoggingIn(true);
            const { data } = await api.logIn(username, password);
            setUser(data);
        } catch {
            alert("Logging in failed");
            logOut();
        } finally {
            setIsLoggingIn(false);
        }
    }

    const getCurrentUser = async () => {
        try {
            setIsFetching(true);

            if (localStorage.getItem("token")) {
                const { data } = await api.getCurrentUser();
                setUser(data);
            }
        } catch {
            alert("Getting current user failed");
            logOut();
        } finally {
            setIsFetching(false);
        }
    }

    return {
        user,
        isLoggingIn,
        isFetching,
        isAuthenticated,
        isAdmin,
        logIn,
        logOut,
        getCurrentUser
    }
};