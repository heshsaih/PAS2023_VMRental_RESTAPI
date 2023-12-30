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
        pathname: Pathnames.public.users,
        Component: UsersPageComponent
    },
    {
        pathname: Pathnames.public.devices,
        Component: DevicesPageComponent
    },
    {
        pathname: Pathnames.public.rents,
        Component: RentsPageComponent
    },
    {
        pathname: Pathnames.public.login,
        Component: LogInPageComponent
    }
]