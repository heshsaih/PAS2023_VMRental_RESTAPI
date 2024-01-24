import Navbar from "../Navbar.tsx";
import {NavbarItem} from "../../types/NavbarItem.ts";
import {ChangePasswordIcon, HomePageIcon, LogInPageIcon, RentsPageIcon} from "../../assets/NavbarIcons.tsx";

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
            path: "/change-password",
            icon: <ChangePasswordIcon></ChangePasswordIcon>,
            label: "Change password"
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