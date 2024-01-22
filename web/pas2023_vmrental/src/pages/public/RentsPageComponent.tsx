import { useEffect, useState } from "react";
import { api } from "../../api/api";
import RentComponent from "../../components/Rent";
import { CreateRentType, RentType } from "../../types/Rent";
import ModalComponent from "../../components/Modal";
import { VirtualDeviceType } from "../../types/VirtualDevice";
import { UserType } from "../../types/User";
import { validateCreateRent } from "../../validator/validator";
import { VirtualDeviceTypeEnum } from "../../enums/VirtualDeviceType.enum";

const RentsPageComponent = () => {
    const [isLoading, setIsLoading] = useState(true);
    const [rents, setRents] = useState<RentType[]>([]);
    const [displayCreateRent, setDisplayCreateRent] = useState(false);
    const closeModal = () => setDisplayCreateRent(false);
    const openModal = () => setDisplayCreateRent(true);

    const CreateRentBody = () => {
        const setDate = () => {
            const d = new Date();
            return (new Date(d.getTime() - d.getTimezoneOffset() * 60000).toISOString()).slice(0, -1);
        };

        const [isLoading, setIsLoading] = useState(true);
        const [devices, setDevices] = useState<VirtualDeviceType[]>();
        const [clients, setClients] = useState<UserType[]>();
        const [newRent, setNewRent] = useState<CreateRentType>({
            startLocalDateTime: "",
            endLocalDateTime: "",
            userId: "",
            virtualDeviceId: ""
        });

        const getData = async () => {
            try {
                setIsLoading(true);
                const fetchedDevices = await api.getAllVirtualDevices();
                const fetchedUsers = await api.getAllUsers(); 
                if (fetchedDevices.status === 200 && fetchedUsers.status === 200) {
                    const devices: VirtualDeviceType[] = [];
                    fetchedDevices.data.forEach(device => {
                        if (device.databaseType) {
                            device.type = VirtualDeviceTypeEnum.VIRTUAL_DATABASE_SERVER
                        } else if (device.operatingSystemType) {
                            device.type = VirtualDeviceTypeEnum.VIRTUAL_MACHINE;
                        } else if (device.phoneNumber) {
                            device.type = VirtualDeviceTypeEnum.VIRTUAL_PHONE;
                        }
                        devices.push(device);
                    });
                    const clients = fetchedUsers.data.filter(user => user.clientType)
                    setDevices(devices);
                    setClients(clients);
                    setNewRent({
                        startLocalDateTime: setDate(),
                        endLocalDateTime: setDate(),
                        userId: clients[0].id,
                        virtualDeviceId: devices[0].id
                    });
                } else {
                    alert("There was an error while getting data from the server");
                }
            } catch(error) {
                console.error(error);
            } finally {
                setIsLoading(false);
            }
        };

        useEffect(() => {
            getData();
        }, []);

        const updateNewRent = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
            setNewRent({
                ...newRent,
                [event.target.name]: event.target.value
            });
            console.log(newRent);
        };

        const createRent = async () => {
            if (confirm("Are you sure you want to proceed?")) {
                const newRentCopy = {...newRent};
                const result = await validateCreateRent(newRentCopy);
                if (result.inner) {
                    let response = "Invalid data\n\n";
                    result.inner.forEach((error: {message: string}) => response += (error.message + "\n"));
                    alert(response);
                } else {
                    const response = await api.createRent(newRentCopy);
                    if (response.status === 200) {
                        alert("New rent has been created successfully!");
                        closeModal();
                        await getRents();
                    } else {
                        alert(`Rent's creation failed with status code ${response.request.status} and message:\n\n${response.request.responseText}`);
                    }
                }
            }
        };

        return (
            <div id="modal-body">
                <h1>Rent a device</h1>
                { isLoading && <p>Loading...</p> }
                { !isLoading && devices && clients &&
                 <div className="details" id="create-rent-container">
                    <div className="value">
                        <h3>Client</h3>
                    </div>
                    <div className="value">
                        <select name="userId" id="create-rent-user" className="select-input" onChange={e => updateNewRent(e)}>
                            {clients.map((x, _i) => {
                                return <option value={x.id}>{x.username}</option>
                            })}
                        </select>
                    </div>
                    <div className="value">
                        <h3>Device</h3>
                    </div>
                    <div className="value">
                        <select name="virtualDeviceId" id="create-rent-device" className="select-input" onChange={e => updateNewRent(e)}>
                            {devices.map((x, _i) => {
                                return <option value={x.id}>{x.type}, {x.id}</option>
                            })}
                        </select>
                    </div>
                    <div className="value">
                        <h3>Start date</h3>
                    </div>
                    <div className="value">
                        <input name="startLocalDateTime" value={newRent.startLocalDateTime} type="datetime-local" onChange={e => updateNewRent(e)}/>
                    </div>
                    <div className="value">
                        <h3>End date</h3>
                    </div>
                    <div className="value">
                        <input name="endLocalDateTime" value={newRent.endLocalDateTime} type="datetime-local" onChange={e => updateNewRent(e)}/>
                    </div>
                </div> }
                {!isLoading && devices && clients && <button className="button" onClick={createRent}>Rent a device</button>}
            </div>
        );
    }

    const getRents = async () => {
        try {
            setIsLoading(true);
            const fetchedRents = await api.getAllRents();
            const fetchedUsers = await api.getAllUsers();
            const fetchedDevices = await api.getAllVirtualDevices();
            if (fetchedDevices.status === 200 && fetchedUsers.status === 200 && fetchedRents.status === 200) {
                const response: RentType[] = [];
                console.log(fetchedRents)
                fetchedRents.data.forEach(rent => {
                    const temp: RentType = {
                        ...rent,
                        renterUsername: fetchedUsers.data.filter(user => user.id === rent.userId)[0].username,
                        virtualDeviceType: null
                    };
                    const rentedDevice = fetchedDevices.data.filter(device => device.id === temp.virtualDeviceId)[0];
                    if (rentedDevice.databaseType) {
                        temp.virtualDeviceType = VirtualDeviceTypeEnum.VIRTUAL_DATABASE_SERVER
                    } else if (rentedDevice.operatingSystemType) {
                        temp.virtualDeviceType = VirtualDeviceTypeEnum.VIRTUAL_MACHINE;
                    } else if (rentedDevice.phoneNumber) {
                        temp.virtualDeviceType = VirtualDeviceTypeEnum.VIRTUAL_PHONE;
                    }
                    response.push(temp);
                });
                setRents(response);
            } else {
                alert("There was an error while getting data from the server");
            }
        } catch (error) {
            console.log(error);
        } finally {
            setIsLoading(false);
        }
    }

    useEffect(() => {
        getRents();
    }, []);

    return (
        <div id="rents" className="page-container">
            <h1>Rents</h1>
            <div id="rent-device-buttons">
                <button className="button" onClick={openModal}>Rent a device</button>
            </div>
            <div id="rents-container">
                { isLoading && <h3>Loading...</h3> }
                { !isLoading && rents && rents.map((x, _i) => {
                    return <RentComponent rent={x} getRents={getRents}></RentComponent>
                })}
            </div>
            { displayCreateRent && <ModalComponent Body={CreateRentBody} close={closeModal}></ModalComponent> }
        </div>
    )
};

export default RentsPageComponent;