import { HomePageIcon, LogInPageIcon } from "../../assets/NavbarIcons";
import { NavbarItem } from "../../types/NavbarItem.ts";
import Navbar from "../Navbar.tsx";

const PublicLayout = ({ Component }: {Component: React.FC}) => {
    const navbarItems: NavbarItem[] = [
        {
            path: "/",
            icon: <HomePageIcon></HomePageIcon>,
            label: "Home"
        },
        {
            path: "/login",
            icon: <LogInPageIcon></LogInPageIcon>,
            label: "Log in"
        }
    ]

    return (
        <div id="public" className={"layout"}>
            <Navbar navbarItems={navbarItems}></Navbar>
            <div id="component">
                <Component></Component>
            </div>
        </div>
    )
};

export default PublicLayout;
