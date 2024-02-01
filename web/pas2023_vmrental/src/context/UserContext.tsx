import {UserType} from "../types/User.ts";
import {createContext, useContext, useState} from "react";

interface UserStateType {
    user: UserType | null,
    setUser: (newUser: UserType | null) => void,
    isLoggingIn: boolean,
    setIsLoggingIn: (state: boolean) => void,
    isFetching: boolean,
    setIsFetching: (state: boolean) => void,
    etag: string | null,
    setEtag: (etag: string) => void
}

const UserStateContext = createContext<UserStateType | null>(null);

export const UserStateContextProvider = ({ children }: { children: React.ReactNode }) => {
    const [user, setUser] = useState<UserType | null>(null);
    const [isLoggingIn, setIsLoggingIn] = useState(false);
    const [isFetching, setIsFetching] = useState(true);
    const [etag, setEtag] = useState(null);

    return (
        <UserStateContext.Provider value={{ user, setUser, isLoggingIn, setIsLoggingIn, isFetching, setIsFetching, etag, setEtag }}>
            { children }
        </UserStateContext.Provider>
    )
}

export const useUserState = () => {
    const userState = useContext(UserStateContext);

    if (!userState) {
        throw new Error("amogus");
    }

    return userState;
}