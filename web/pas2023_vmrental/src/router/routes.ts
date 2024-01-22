import DevicesPageComponent from "../pages/public/DevicesPageComponent";
import HomePageComponent from "../pages/public/HomePageComponent";
import LogInPageComponent from "../pages/public/LogInPageComponent";
import RentsPageComponent from "../pages/public/RentsPageComponent";
import UsersPageComponent from "../pages/public/UsersPageComponent";
import { RouteType } from "../types/Route";
import { Pathnames } from "./pathnames";

export const publicRoutes: RouteType[] = [
    {
        pathname: Pathnames.public.home,
        Component: HomePageComponent
    },
    {
        pathname: Pathnames.public.login,
        Component: LogInPageComponent
    }
]

export const userRoutes: RouteType[] = [
    {
        pathname: Pathnames.user.rents,
        Component: RentsPageComponent
    }
]

export const adminRoutes: RouteType[] = [
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
    }
]

export const resourceManagerRoutes: RouteType[] = [
    {
        pathname: Pathnames.resourceManager.devices,
        Component: DevicesPageComponent
    }
]