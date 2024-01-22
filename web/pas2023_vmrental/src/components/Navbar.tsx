import {NavbarItem} from "../types/NavbarItem.ts";
import {NavLink} from "react-router-dom";

const NavbarComponent = ({ navbarItems }: { navbarItems: NavbarItem[] }) => {
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

export default NavbarComponent;