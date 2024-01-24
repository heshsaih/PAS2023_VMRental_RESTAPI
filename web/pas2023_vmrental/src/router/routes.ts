import DevicesPageComponent from "../pages/public/DevicesPageComponent";
import HomePageComponent from "../pages/public/HomePageComponent";
import LogInPageComponent from "../pages/public/LogInPageComponent";
import RentsPageComponent from "../pages/public/RentsPageComponent";
import UsersPageComponent from "../pages/public/UsersPageComponent";
import { RouteType } from "../types/Route";
import { Pathnames } from "./pathnames";
import LogOutPageComponent from "../pages/public/LogOutPageComponent.tsx";
import ErrorPageComponent from "../pages/public/ErrorPage.tsx";
import ChangePasswordPageComponent from "../pages/public/ChangePasswordPageComponent.tsx";

export const publicRoutes: RouteType[] = [
    {
        pathname: Pathnames.public.home,
        Component: HomePageComponent
    },
    {
        pathname: Pathnames.public.login,
        Component: LogInPageComponent
    },
    {
        pathname: "*",
        Component: ErrorPageComponent
    }
]

export const userRoutes: RouteType[] = [
    {
        pathname: Pathnames.user.home,
        Component: HomePageComponent
    },
    {
        pathname: Pathnames.user.rents,
        Component: RentsPageComponent
    },
    {
        pathname: Pathnames.user.logout,
        Component: LogOutPageComponent
    },
    {
        pathname: Pathnames.user.changePassword,
        Component: ChangePasswordPageComponent
    },
    {
        pathname: "*",
        Component: ErrorPageComponent
    }
]

export const adminRoutes: RouteType[] = [
    {
        pathname: Pathnames.public.home,
        Component: HomePageComponent
    },
    {
        pathname: Pathnames.admin.rents,
        Component: RentsPageComponent
    },
    {
        pathname: Pathnames.admin.devices,
        Component: DevicesPageComponent
    },
    {
        pathname: Pathnames.admin.users,
        Component: UsersPageComponent
    },
    {
        pathname: Pathnames.admin.logout,
        Component: LogOutPageComponent
    },
    {
        pathname: Pathnames.admin.changePassword,
        Component: ChangePasswordPageComponent
    },
    {
        pathname: "*",
        Component: ErrorPageComponent
    }
]

export const resourceManagerRoutes: RouteType[] = [
    {
        pathname: Pathnames.resourceManager.home,
        Component: HomePageComponent
    },
    {
        pathname: Pathnames.resourceManager.devices,
        Component: DevicesPageComponent
    },
    {
        pathname: Pathnames.resourceManager.logout,
        Component: LogOutPageComponent
    },
    {
        pathname: Pathnames.resourceManager.changePassword,
        Component: ChangePasswordPageComponent
    },
    {
        pathname: "*",
        Component: ErrorPageComponent
    }
]