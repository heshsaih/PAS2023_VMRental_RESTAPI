import {  useEffect, useState } from "react";
import { ModifyUserType, UserType } from "../types/User";
import ModalComponent from "./Modal";
import { api } from "../api/api";
import { RentType } from "../types/Rent";
import { validateModifiedUser } from "../validator/validator";

const UserComponent = ({ user, getUsers }: { user: UserType, getUsers: () => Promise<void>}) => {
    const [displayUserDetails, setDisplayUserDetails] = useState(false);
    const [displayUserModify, setDisplayUserModify] = useState(false);
    const closeUserModify = () => setDisplayUserModify(false);
    const openUserModify = () => setDisplayUserModify(true);
    const closeUserDetails = () => setDisplayUserDetails(false);
    const openUserDetails = () => setDisplayUserDetails(true); 

    const UserModifyBody = () => {
        const [newUser, setNewUser] = useState<ModifyUserType>({
            id: user.id,
            firstName: user.firstName,
            lastName: user.lastName,
            password: null,
            email: user.email,
            address: user.address
        });

        const updateNewUser = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
            setNewUser({
                ...newUser,
                [event.target.name]: event.target.value
            });
            console.log(newUser)
        };

        const updateNewUsersAddress = (event: React.ChangeEvent<HTMLInputElement>) => {
            setNewUser({
                ...newUser,
                address: {
                    ...newUser.address,
                    [event.target.name]: event.target.value
                }
            });
            console.log(newUser)
        };

        const modifyUser = async () => {
            if (confirm("Are you sure you want to proceed?")) {
                const newUserCopy = {...newUser};
                const result = await validateModifiedUser(newUserCopy);
                if (result.inner) {
                    let response = "Invalid data\n\n";
                    result.inner.forEach(error => response += (error.message + "\n"));
                    alert(response);
                } else {
                    const response = await api.modifyUser(newUserCopy.id, newUserCopy);
                    if (response.status === 200) {
                        alert("User has been modified successfully");
                        closeUserDetails();
                        closeUserModify();
                        await getUsers();
                    } else {
                        alert(`User's modification failed with status code ${response.request.status} and message:\n\n${response.request.responseText}`);
                    }
                }
            }
        };

        return (
            <div id="modal-body">
                <h1>Modify user</h1>
                <div id="fields">
                    <div className="value">
                        <h3>First name</h3>
                    </div>
                    <div className="value">
                        <input value={newUser.firstName} placeholder="Enter new first name" type="text" name="firstName" id="firstname-text-input" className="text-input" onChange={(e) => updateNewUser(e)}/>
                    </div>
                    <div className="value">
                         <h3>Last name</h3>
                    </div>
                    <div className="value">
                        <input value={newUser.lastName} placeholder="Enter new last name" type="text" name="lastName" id="lastname-text-input" className="text-input" onChange={(e) => updateNewUser(e)}/>
                    </div>
                    <div className="value">
                         <h3>Password</h3>
                    </div>
                    <div className="value">
                        <input placeholder="Enter new password" type="password" name="password" id="password-text-input" className="text-input" onChange={(e) => updateNewUser(e)}/>
                    </div>
                    <div className="value">
                         <h3>Email</h3>
                    </div>
                    <div className="value">
                        <input value={newUser.email} placeholder="Enter new email" type="text" name="email" id="email-text-input" className="text-input" onChange={(e) => updateNewUser(e)}/>
                    </div>
                    <div className="value">
                         <h3>Address</h3>
                    </div>
                    <div id="new-user-address" className="value">
                        <input value={newUser.address.street} placeholder="Enter street name" type="text" name="street" id="address-street-text-input" className="text-input" onChange={(e) => updateNewUsersAddress(e)}/>
                        <input value={newUser.address.city} placeholder="Enter city name" type="text" name="city" id="address-city-text-input" className="text-input" onChange={(e) => updateNewUsersAddress(e)}/>
                        <input value={newUser.address.houseNumber} placeholder="Enter house number" type="text" name="houseNumber" id="street-housenumber-text-input" className="text-input" onChange={(e) => updateNewUsersAddress(e)}/>
                    </div>
                </div>
                <button className="button" onClick={modifyUser}>Modify user</button>
            </div>
        );
    }

    const UserDetailsBody = () => {
        const [rents, setRents] = useState<string[]>([]);
        const [loading, setLoading] = useState(true);
    
        const parseAddress = () => {
            return `${user.address.street} ${user.address.houseNumber}, ${user.address.city}`;
        };

        const activateUser = async () => {
            if(confirm("Are you sure you want to proceed?")) {
                const response =  await api.setUserActive(user.id);
                if (response.status === 200) {
                    alert("User has been activated successfully!");
                    await getUsers();
                    closeUserDetails();
                } else {
                    alert(response.data);
                }
            }
        };

        const deactivateUser = async () => {
            if(confirm("Are you sure you want to proceed?")) {
                const response =  await api.setUserInactive(user.id);
                if (response.status === 200) {
                    alert("User has been deactivated successfully!");
                    await getUsers();
                    closeUserDetails();
                } else {
                    alert("User's deactivation failed");
                }
                
            }
        };

        const parseRents = (rents: RentType[]) => {
            const parsedRents: string[] = [];
            rents.forEach(rent => {
                parsedRents.push(
                    `Device ID: ${rent.virtualDeviceId}
                    Start date: ${rent.startLocalDateTime}
                    End date: ${rent.endLocalDateTime}`
                );
            }); 
            setRents(parsedRents)
        } 
    
        const getUsersRentsAndParseToReadable = async () => {
            try {
                setLoading(true);
                const { data } = await api.getUsersRents(user.id);
                parseRents(data);
            } catch (error) {
                console.error(error);
            } finally {
                setLoading(false);
            }
        };
    
        useEffect(() => {
            getUsersRentsAndParseToReadable();
        }, []);
        return (
            <div id="modal-body">
                <h1>User details</h1>
                <div id="activity-button">
                    { user.active && <button className="button" onClick={deactivateUser}>Set user inactive</button> }
                    { !user.active && <button className="button" onClick={activateUser}>Set user active</button> }
                    <button className="button" onClick={openUserModify}>Modify user</button>
                </div>
                <div className="details">
                    <div className="value">
                        <h3>Username</h3>
                    </div>
                    <div className="value">
                        <p>{user.username}</p>
                    </div>
                    <div className="value">
                        <h3>Full name</h3>
                    </div>
                    <div className="value">
                        <p>{`${user.firstName} ${user.lastName}`}</p>
                    </div>
                    <div className="value">
                        <h3>Address</h3>
                    </div>
                    <div className="value">
                        <p>{parseAddress()}</p>
                    </div>
                    <div className="value">
                        <h3>E-mail</h3>
                    </div>
                    <div className="value">
                        <p>{user.email}</p>
                    </div>
                    <div className="value">
                        <h3>User type</h3>
                    </div>
                    <div className="value">
                        <p>{user.clientType ? `${user.userType}, ${user.clientType}` : user.userType}</p>
                    </div>
                    <div className="value">
                        <h3>ID</h3>
                    </div>
                    <div className="value">
                        <p>{user.id}</p>
                    </div>
                    <div className="value">
                        <h3>Is active</h3>
                    </div>
                    <div className="value">
                        {user.active ? <p style={{color: "green"}}>Active</p> : <p style={{color: "red"}}>Inactive</p>}
                    </div>
                </div>
                <div id="users-rents">
                    <h2>Rents</h2>
                    <div id="users-rents-container">
                        {loading && <p>Loading...</p>}
                        {!loading && rents && <ul>
                            {rents.map((x, i) => {
                                return <li key={i}>{x.split("\n").map(str => <p>{str}</p>)}</li>
                            })}
                        </ul>}
                        { !loading && rents.length === 0 && <p>This user has no rents</p> }
                    </div>
                </div>
            </div>
        )
    };

    return (
        <div className="list-element" onClick={openUserDetails}>
            <h2>{user.username}</h2>
            { user.clientType && <h3>{user.clientType}</h3> }
            { !user.clientType && <h3>{user.userType}</h3> }
            { user.active ? <p style={{color: "green"}}>Active</p> : <p style={{color: "red"}}>Inactive</p> }
            { displayUserDetails ? <ModalComponent close={(e) => {
                e.stopPropagation();
                closeUserDetails();
            }} Body={UserDetailsBody}></ModalComponent> : <></> }
            { displayUserModify ? <ModalComponent close={e => {
                e.stopPropagation();
                closeUserModify();
            }} Body={UserModifyBody} ></ModalComponent> : <></>}
        </div>
    )
};

export default UserComponent;