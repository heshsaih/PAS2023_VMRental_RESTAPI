import React, {useState} from "react";
import {useUser} from "../../hooks/useUser.ts";
import {ModifyUserType} from "../../types/User.ts";
import {api} from "../../api/api.ts";
import axios from "axios";

const ChangePasswordPageComponent = () => {
    const { user } = useUser();
    const [newPassword, setNewPassword] = useState("");

    const changePassword = async () => {
        if (user && confirm("Are you sure you want to proceed?")) {
            const copy: ModifyUserType = {
                id: user.id,
                firstName: user.firstName,
                lastName: user.lastName,
                password: newPassword,
                email: user.email,
                address: user.address,
                clientType: user.clientType
            }
            try {
                const response = await api.modifyCurrentUser(copy);
                if (response.status === 200) {
                    alert("Password has been changed!");
                }
            } catch (error) {
                if (axios.isAxiosError(error)) {
                    alert(`${error.message}: ${error.response?.data}`)
                }
            }
        }
    }

    const handleClick = (e: React.KeyboardEvent) => {
        if (e.key === "Enter") {
            changePassword();
        }
    }

    return (
        <div id={"change-password"} className={"page-container"}>
            <h1>Change password</h1>
            <label htmlFor="new-password">New password</label>
            <input onKeyPress={e => handleClick(e)} value={newPassword} onChange={e => setNewPassword(e.target.value)} className={"text-input"} placeholder={"Enter new password"} name={"new-password"} id={"new-password"} type="password"/>
            <button className={"button"} onClick={changePassword}>Change password</button>
        </div>
    )
}

export default ChangePasswordPageComponent;