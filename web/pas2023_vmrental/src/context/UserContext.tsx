import { ReactNode, createContext, useContext, useEffect, useState } from "react";
import { UserType } from "../types/User";

interface UserState {
    user: UserType | null,
    setUser: (item: UserType | null) => void,
    isLoggingIn: boolean,
    setIsLoggingIn: (value: boolean) => void,
    isFetching: boolean,
    setIsFetching: (value: boolean) => void
}

const UserStateContext = createContext<UserState | null>(null);

export const UserStateProvider = ({children}: {children: ReactNode}) => {
    const [user, setUser] = useState<UserType | null>(null);
    const [isLoggingIn, setIsLoggingIn] = useState(false);
    const [isFetching, setIsFetching] = useState(true);

    useEffect(() => {
        if (user?.token) {
            localStorage.setItem("token", JSON.stringify(user.token));
        }
    }, [user])

    return (
        <UserStateContext.Provider value={{ user, setUser, isLoggingIn, setIsLoggingIn, isFetching, setIsFetching }}>
            {children}
        </UserStateContext.Provider>
    );
};

export const useUserState = () => {
    const userState = useContext(UserStateContext);

    if (!userState) {
        throw new Error("amogus");
    }

    return userState;
};