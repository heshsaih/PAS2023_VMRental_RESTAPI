import { Route, Routes } from "react-router-dom";
import {adminRoutes, publicRoutes, resourceManagerRoutes, userRoutes} from "../routes";
import PublicLayout from "../../components/layouts/PublicLayout";
import {useUser} from "../../hooks/useUser.ts";
import {useEffect} from "react";
import ErrorPageComponent from "../../pages/public/ErrorPage.tsx";
import UserLayout from "../../components/layouts/UserLayout.tsx";
import AdminLayoutComponent from "../../components/layouts/AdminLayout.tsx";
import ResourceManagerLayoutComponent from "../../components/layouts/ResourceManagerLayout.tsx";



export const RoutesComponent = () => {
    const { user, isAuthenticated, isAdmin, isResourceManager, isFetching, getCurrentUser } = useUser();

    useEffect(() => {
        if (!user) {
            getCurrentUser();
        }
    }, []);

    if (isFetching) {
        return (<h1>Loading...</h1>);
    }

    return (
        <Routes>
            { !isAuthenticated && publicRoutes.map(({ pathname, Component }) => {
                console.log("4");
                return <Route key={pathname} path={pathname} element={<PublicLayout Component={Component}></PublicLayout>}></Route>;
            })}

            { isAuthenticated && isAdmin && adminRoutes.map(({ pathname, Component }) => {
                console.log("2");
                return <Route key={pathname} path={pathname} element={<AdminLayoutComponent Component={Component}></AdminLayoutComponent>}></Route>;
            })}

            { isAuthenticated && isResourceManager && resourceManagerRoutes.map(({ pathname, Component }) => {
                console.log("3");
                return <Route key={pathname} path={pathname} element={<ResourceManagerLayoutComponent Component={Component}></ResourceManagerLayoutComponent>}></Route>
            })}

            { isAuthenticated && userRoutes.map(({ pathname, Component }) => {
                console.log("1");
                return <Route key={pathname} path={pathname} element={<UserLayout Component={Component}></UserLayout>}></Route>
            })}
        </Routes>
    );
};