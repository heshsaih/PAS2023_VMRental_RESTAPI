import {useUser} from "../../hooks/useUser.ts";
import {useEffect} from "react";
import {Navigate} from "react-router-dom";

const LogOutPageComponent = () => {
    const { logOut } = useUser();

    useEffect(() => {
        logOut();
    }, []);

    return (
        <Navigate to={"/"}></Navigate>
    );
}

export default LogOutPageComponent;
