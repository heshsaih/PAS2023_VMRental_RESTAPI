import {useUserState} from "../context/UserContext.tsx";
import {UserTypeEnum} from "../enums/UserType.enum.ts";
import {SignInType} from "../types/SignIn.ts";
import {api} from "../api/api.ts";
import {useNavigate} from "react-router-dom";
import {Pathnames} from "../router/pathnames.ts";

export const useUser = () => {
    const { user, setUser, isLoggingIn, setIsLoggingIn, isFetching, setIsFetching } = useUserState();
    const navigate = useNavigate();
    const isAuthenticated = user?.userType === UserTypeEnum.CLIENT;
    const isAdmin = user?.userType === UserTypeEnum.ADMINISTRATOR;
    const isResourceManager = user?.userType === UserTypeEnum.RESOURCE_MANAGER;

    const logIn = async (signInData: SignInType) => {
        try {
            setIsLoggingIn(true);
            const { data } = await api.logIn(signInData);
            setUser(data);
            navigate(Pathnames.user.home);
        } catch (error) {
            console.error(error);
            alert("Loggin in has failed");
        } finally {
            setIsLoggingIn(false);
        }
    }

    const logOut = async () => {
        try {
            setIsFetching(true);
            await api.logOut();
        } catch (error) {
            console.error(error);
            alert("Logging out has failed");
        } finally {
            localStorage.removeItem("token");
            setUser(null);
            setIsFetching(false);
            navigate(Pathnames.public.home);
        }
    }

    const getCurrentUser = async () => {
        try {
            setIsFetching(true);
            if (localStorage.getItem("token")) {
                const { data } = await api.getCurrentUser();
                setUser(data);
                console.log(data);
            }
        } catch (error) {
            console.error(error);
            alert("Can't find current user");
        } finally {
            setIsFetching(false);
        }
    }

    return {
        user,
        isLoggingIn,
        isFetching,
        isAuthenticated,
        isResourceManager,
        isAdmin,
        logIn,
        getCurrentUser,
        logOut
    }
}