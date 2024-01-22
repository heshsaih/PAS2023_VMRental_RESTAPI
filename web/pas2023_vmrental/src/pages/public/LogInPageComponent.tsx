import React, {useEffect, useState} from "react";
import {SignInType} from "../../types/SignIn.ts";
import {useUser} from "../../hooks/useUser.ts";
import {Navigate} from "react-router-dom";
import {Pathnames} from "../../router/pathnames.ts";
const LogInPageComponent = () => {
    const { isAuthenticated, logIn, getCurrentUser } = useUser();
    const [loginData, setLoginData] = useState<SignInType>({
        username: "",
        password: ""
    });

    const updateLoginData = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLoginData({...loginData, [e.target.name]: e.target.value});
    }

    const submit = async () => {
        try {
            await logIn(loginData);
        } catch (error) {
            console.error(error);
            alert("Login failed");
        }
    }

    useEffect(() => {
        getCurrentUser();
    }, []);

    if (isAuthenticated) {
        return (
            <Navigate to={Pathnames.user.home} replace></Navigate>
        );
    }

    return (
        <div className={"page-container"}>
            <h1>Log in</h1>
            <label htmlFor="username">Username</label>
            <input className={"text-input"} placeholder={"Enter username"} name={"username"} id={"username"} value={loginData.username} onChange={e => updateLoginData(e)} type="text"/>
            <label htmlFor="password">Password</label>
            <input className={"text-input"} placeholder={"Enter password"} name={"password"} id={"password"} value={loginData.password} onChange={e => updateLoginData(e)} type="password"/>
            <button onClick={submit} className={"button"}>Log in</button>
        </div>
    )
};

export default LogInPageComponent;