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
            { isAuthenticated && userRoutes.map(({ pathname, Component }) => {
                return <Route key={pathname} path={pathname} element={<UserLayout Component={Component}></UserLayout>}></Route>
            }) }

            { isAuthenticated && isAdmin && adminRoutes.map(({ pathname, Component }) => {
                return <Route key={pathname} path={pathname} element={<AdminLayoutComponent Component={Component}></AdminLayoutComponent>}></Route>;
            }) }

            { isAuthenticated && isResourceManager && resourceManagerRoutes.map(({ pathname, Component }) => {
                return <Route key={pathname} path={pathname} element={<ResourceManagerLayoutComponent Component={Component}></ResourceManagerLayoutComponent>}></Route>
            }) }

            { !isAuthenticated && publicRoutes.map(({ pathname, Component }) => {
                return <Route key={pathname} path={pathname} element={<PublicLayout Component={Component}></PublicLayout>}></Route>;
            })}
            <Route path="*" element={<PublicLayout Component={ErrorPageComponent}></PublicLayout>}></Route>
        </Routes>
    );
};