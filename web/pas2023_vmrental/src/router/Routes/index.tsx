import { Route, Routes } from "react-router-dom";
import { publicRoutes } from "../routes";
import HomePageComponent from "../../pages/public/HomePageComponent";
import PublicLayout from "../../components/layouts/PublicLayout";



export const RoutesComponent = () => {
    return (
        <Routes>
            {publicRoutes.map(({ pathname, Component }) => {
                return <Route key={pathname} path={pathname} element={<PublicLayout Component={Component}></PublicLayout>}></Route>;
            })}
            <Route path="*" Component={HomePageComponent}></Route>
        </Routes>
    );
};