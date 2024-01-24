import Navbar from "../Navbar.tsx";
import {NavbarItem} from "../../types/NavbarItem.ts";
import {DevicesPageIcon, HomePageIcon, LogInPageIcon} from "../../assets/NavbarIcons.tsx";

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