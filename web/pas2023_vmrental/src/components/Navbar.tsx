import {NavbarItem} from "../types/NavbarItem.ts";
import {NavLink} from "react-router-dom";
import {useUser} from "../hooks/useUser.ts";

const NavbarComponent = ({ navbarItems }: { navbarItems: NavbarItem[] }) => {
    const { user, isAuthenticated } = useUser();
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
                { isAuthenticated && user?.userType && <div className={"user-info"}>
                    <h3>Welcome</h3>
                    <p>{ user.username }</p>
                    <p>{ user.userType }</p>
                </div> }
            </ul>
        </div>
    );
};

export default NavbarComponent;