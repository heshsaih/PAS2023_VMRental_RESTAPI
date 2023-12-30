import { NavLink } from "react-router-dom";
import { DevicesPageIcon, HomePageIcon, LogInPageIcon, RentsPageIcon, UsersPageIcon } from "../../assets/NavbarIcons";

type NavbarItem = {
    path: string,
    icon: React.ReactNode,
    label: string
};

const Navbar = () => {
    const navbarItems: NavbarItem[] = [
        {
            path: "/",
            icon: <HomePageIcon></HomePageIcon>,
            label: "Home"
        },
        {
            path: "/users",
            icon: <UsersPageIcon></UsersPageIcon>,
            label: "Users"
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
            path: "/login",
            icon: <LogInPageIcon></LogInPageIcon>,
            label: "Log in"
        }
    ];

    return (
        <div id="navbar">
            <ul id="navbar-items">
                { navbarItems.map(({ path, icon, label }) => {
                    return <li key={path} className="navbar-item">
                        <NavLink to={path} className="navbar-link">
                            { icon }
                            <p className="navbar-item-label">{label}</p>
                        </NavLink>
                    </li>
                }) }
            </ul>
        </div>
    );
};

const PublicLayout = ({ Component }: {Component: React.FC}) => {
    return (
        <div id="public-layout">
            <Navbar></Navbar>
            <div id="component">
                <Component></Component>
            </div>
        </div>
    )
};

export default PublicLayout;
