import React, {useEffect, useState} from "react";
import {SignInType} from "../../types/SignIn.ts";
import {useUser} from "../../hooks/useUser.ts";
const LogInPageComponent = () => {
    const { logIn, getCurrentUser } = useUser();
    const [loginData, setLoginData] = useState<SignInType>({
        username: "",
        password: ""
    });

    const updateLoginData = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLoginData({...loginData, [e.target.name]: e.target.value});
    }

    const submit = async ()=> {
        await logIn(loginData);
    }

    useEffect(() => {
        getCurrentUser();
    }, []);

    const handleClick = (e: React.KeyboardEvent) => {
        if (e.key === "Enter") {
            submit();
        }
    }

    return (
        <div className={"page-container"}>
            <h1>Log in</h1>
            <label htmlFor="username">Username</label>
            <input onKeyDown={e => handleClick(e)} className={"text-input"} placeholder={"Enter username"} name={"username"} id={"username"} value={loginData.username} onChange={e => updateLoginData(e)} type="text"/>
            <label htmlFor="password">Password</label>
            <input onKeyDown={e => handleClick(e)} className={"text-input"} placeholder={"Enter password"} name={"password"} id={"password"} value={loginData.password} onChange={e => updateLoginData(e)} type="password"/>
            <button onClick={submit} className={"button"}>Log in</button>
        </div>
    )
};

export default LogInPageComponent;