import Navbar from "../Navbar.tsx";
import {NavbarItem} from "../../types/NavbarItem.ts";
import {ChangePasswordIcon, DevicesPageIcon, HomePageIcon, LogInPageIcon} from "../../assets/NavbarIcons.tsx";

const ResourceManagerLayoutComponent = ({ Component }: { Component: React.FC }) => {
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
            path: "/change-password",
            icon: <ChangePasswordIcon></ChangePasswordIcon>,
            label: "Change password"
        },
        {
            path: "/logout",
            icon: <LogInPageIcon></LogInPageIcon>,
            label: "Log out"
        }
    ]

    return (
        <div id="resource-manager" className={"layout"}>
            <Navbar navbarItems={navbarItems}></Navbar>
            <div id="component">
                <Component></Component>
            </div>
        </div>
    )
}

export default ResourceManagerLayoutComponent;