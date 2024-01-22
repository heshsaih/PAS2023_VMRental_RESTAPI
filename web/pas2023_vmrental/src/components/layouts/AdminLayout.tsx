import {NavbarItem} from "../../types/NavbarItem.ts";
import {DevicesPageIcon, HomePageIcon, LogInPageIcon, RentsPageIcon, UsersPageIcon} from "../../assets/NavbarIcons.tsx";
import Navbar from "../Navbar.tsx";

const AdminLayoutComponent = ({ Component }: { Component: React.FC }) => {
    const navbarItems: NavbarItem[] = [
        {
            path: "/",
            icon: <HomePageIcon></HomePageIcon>,
            label: "Home"
        },
        {
            path: "/devices",
            icon: <DevicesPageIcon></DevicesPageIcon>,
            label: "Devices"
        },
        {
            path: "/rents",
            icon: <RentsPageIcon></RentsPageIcon>,
            label: "Rents"
        },
        {
            path: "/users",
            icon: <UsersPageIcon></UsersPageIcon>,
            label: "Users"
        },
        {
            path: "/logout",
            icon: <LogInPageIcon></LogInPageIcon>,
            label: "Log out"
        }
    ];

    return (
        <div id="public-layout">
            <Navbar navbarItems={navbarItems}></Navbar>
            <div id="component">
                <Component></Component>
            </div>
        </div>
    )
}

export default AdminLayoutComponent;