import { useEffect, useState } from "react";
import { CreateUserType, UserType } from "../../types/User";
import { api } from "../../api/api";
import UserComponent from "../../components/User";
import Modal from "../../components/Modal";
import { UserTypeEnum } from "../../enums/UserType.enum";
import { ClientTypeEnum } from "../../enums/ClientType.enum";
import { validateUser } from "../../validator/validator";

const UsersPageComponent = () => {
    const [users, setUsers] = useState<UserType[]>([]);
    const [filteredUsers, setFilteredUsers] = useState<UserType[]>([]);
    const [loading, setLoading] = useState(true);
    const [filterContent, setFilterContent] = useState("");
    const [displayCreateUser, setDisplayCreateUser] = useState(false);
    const closeModal = () => setDisplayCreateUser(false);
    const openModal = () => setDisplayCreateUser(true);

    const CreateUserBody = () => {
        const [newUser, setNewUser] = useState<CreateUserType>({
            userType: UserTypeEnum.CLIENT,
            clientType: ClientTypeEnum.BRONZE,
            username: "",
            firstName: "",
            lastName: "",
            password: "",
            email: "",
            address: {
                street: "",
                houseNumber: "",
                city: ""
            }
        });

        const updateNewUser = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
            setNewUser({
                ...newUser,
                [event.target.name]: event.target.value
            });
        };

        const updateNewUsersAddress = (event: React.ChangeEvent<HTMLInputElement>) => {
            setNewUser({
                ...newUser,
                address: {
                    ...newUser.address,
                    [event.target.name]: event.target.value
                }
            });
        };

        const createNewUser = async () => {
            if (confirm("Are you sure you want to proceed?")) {
                const newUserCopy = {...newUser};
                const result = await validateUser(newUserCopy);
                if (result.inner) {
                    let response = "Invalid data\n\n";
                    result.inner.forEach((error: {message: string}) => response += (error.message + "\n"));
                    alert(response)
                } else {
                    switch(newUserCopy.userType) {
                        case UserTypeEnum.CLIENT: {
                            const response = await api.createClient(newUserCopy);
                            if (response.status === 200) {
                                alert("New client has been created successfully!");
                                closeModal();
                                await getUsers();
                            } else {
                                alert(`Client's creation failed with status code ${response.request.status} and message:\n\n${response.request.responseText}`);
                            }
                            break;
                        }
                        case UserTypeEnum.ADMINISTRATOR: {
                            const response = await api.createAdministrator(newUserCopy);
                            if (response.status === 200) {
                                alert("New administrator has been created successfully!");
                                closeModal();
                                await getUsers();
                            } else {
                                alert(`Administrator's creation failed with status code ${response.request.status} and message:\n\n${response.request.responseText}`);
                            }
                            break;
                        }
                        case UserTypeEnum.RESOURCE_MANAGER: {
                            const response = await api.createResourceManager(newUserCopy);
                            if (response.status === 200) {
                                alert("New resource manager has been created successfully!");
                                closeModal();
                                await getUsers();
                            } else {
                                alert(`Resource manager's creation failed with status code ${response.request.status} and message:\n\n${response.request.responseText}`);
                            }
                            break;
                        }
                    }
                }
            }
        };

        return (
            <div id="modal-body" onClick={e => e.stopPropagation()}>
                <h1>Create a user</h1>
                <div id="new-user-inputs" className="details">
                    <div className="value">
                         <h3>Username</h3>
                    </div>
                    <div className="value">
                        <input placeholder="Enter username" type="text" name="username" id="username-text-input" className="text-input" onChange={(e) => updateNewUser(e)}/>
                    </div>
                    <div className="value">
                         <h3>Password</h3>
                    </div>
                    <div className="value">
                        <input placeholder="Enter password" type="password" name="password" id="password-text-input" className="text-input" onChange={(e) => updateNewUser(e)}/>
                    </div>
                    <div className="value">
                         <h3>First name</h3>
                    </div>
                    <div className="value">
                        <input placeholder="Enter first name" type="text" name="firstName" id="firstname-text-input" className="text-input" onChange={(e) => updateNewUser(e)}/>
                    </div>
                    <div className="value">
                         <h3>Last name</h3>
                    </div>
                    <div className="value">
                        <input placeholder="Enter last name" type="text" name="lastName" id="lastname-text-input" className="text-input" onChange={(e) => updateNewUser(e)}/>
                    </div>
                    <div className="value">
                         <h3>E-mail</h3>
                    </div>
                    <div className="value">
                        <input placeholder="Enter e-mail" type="text" name="email" id="email-text-input" className="text-input" onChange={(e) => updateNewUser(e)}/>
                    </div>
                    <div className="value">
                         <h3>User type</h3>
                    </div>
                    <div className="value" id="new-user-types">
                        <select className="select-input" name="userType" id="user-type-input" onChange={e => updateNewUser(e)}>
                            <option value={UserTypeEnum.CLIENT}>{UserTypeEnum.CLIENT}</option>
                            <option value={UserTypeEnum.ADMINISTRATOR}>{UserTypeEnum.ADMINISTRATOR}</option>
                            <option value={UserTypeEnum.RESOURCE_MANAGER}>{UserTypeEnum.RESOURCE_MANAGER}</option>
                        </select>
                        <select className="select-input" name="clientType" id="client-type-input" onChange={e => updateNewUser(e)} disabled={newUser.userType !== UserTypeEnum.CLIENT}>
                            <option value={ClientTypeEnum.BRONZE}>{ClientTypeEnum.BRONZE}</option>
                            <option value={ClientTypeEnum.SILVER}>{ClientTypeEnum.SILVER}</option>
                            <option value={ClientTypeEnum.GOLD}>{ClientTypeEnum.GOLD}</option>
                            <option value={ClientTypeEnum.DIAMOND}>{ClientTypeEnum.DIAMOND}</option>
                        </select>
                    </div>
                    <div className="value">
                         <h3>Address</h3>
                    </div>
                    <div id="new-user-address" className="value">
                        <input placeholder="Enter street name" type="text" name="street" id="address-street-text-input" className="text-input" onChange={(e) => updateNewUsersAddress(e)}/>
                        <input placeholder="Enter house number" type="text" name="houseNumber" id="street-housenumber-text-input" className="text-input" onChange={(e) => updateNewUsersAddress(e)}/>
                        <input placeholder="Enter city name" type="text" name="city" id="address-city-text-input" className="text-input" onChange={(e) => updateNewUsersAddress(e)}/>
                    </div>
                    <div id="create-user-button">
                        <button className="button" onClick={createNewUser}>Create new user</button>
                    </div>
                </div>
            </div>
        );
    };

    const filterUsers = (users: UserType[], filter: string) => {
        return users.filter((object: UserType) => object.username.toLowerCase().includes(filter.toLowerCase()));
    };

    const getUsers = async () => {
        try {
            setLoading(true);
            const response = await api.getAllUsers();
            if (response.status === 200) {
                setUsers(response.data);
                setFilteredUsers(response.data);
            } else {
                alert(`There was an error while getting data from server`);
            }
        } catch(error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        getUsers();
    }, []);

    useEffect(() => {
        setFilteredUsers(filterUsers(users, filterContent))
    }, [filterContent])
    return (
        <div id="users" className="page-container">
            <h1>Users</h1>
            <div id="buttons">
                <button className="button" onClick={openModal}>Create a user</button>
            </div>
            <div id="users-filter">
                <h2>Filter users</h2>
                <input value={filterContent} type="text" id="users-filter-input" onChange={e => setFilterContent(e.target.value)} className="text-input" placeholder="Enter your filter"/>
            </div>
            <div id="users-container">
                {loading && <h3>Loading...</h3>}
                {!loading && filteredUsers && filteredUsers.map((x, i) => {
                    return <UserComponent key={i} user={x} getUsers={getUsers}></UserComponent>
                })}
            </div>
            { displayCreateUser ? <Modal close={closeModal} Body={CreateUserBody}></Modal> : <></>}
        </div>
    );
};

export default UsersPageComponent