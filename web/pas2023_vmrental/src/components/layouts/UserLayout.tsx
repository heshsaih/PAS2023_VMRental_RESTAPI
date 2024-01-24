import Navbar from "../Navbar.tsx";
import {NavbarItem} from "../../types/NavbarItem.ts";
import {HomePageIcon, LogInPageIcon, RentsPageIcon} from "../../assets/NavbarIcons.tsx";

const UserLayoutComponent = ({ Component }: { Component: React.FC }) => {
    const navbarItems: NavbarItem[] = [
        {
            path: "/",
            icon: <HomePageIcon></HomePageIcon>,
            label: "Home"
        },
        {
            path: "/rents",
            icon: <RentsPageIcon></RentsPageIcon>,
            label: "Rents"
        },
        {
            path: "/logout",
            icon: <LogInPageIcon></LogInPageIcon>,
            label: "Log out"
        }
    ];
    return (
        <div id="user" className={"layout"}>
            <Navbar navbarItems={navbarItems}></Navbar>
            <div id="component">
                <Component></Component>
            </div>
        </div>
    )
}

export default UserLayoutComponent;