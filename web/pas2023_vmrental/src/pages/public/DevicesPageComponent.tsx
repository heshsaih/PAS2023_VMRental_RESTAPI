import {useEffect, useState} from "react";
import {api} from "../../api/api.ts";

const DevicesPageComponent = () => {
    const [devices, setDevices] = useState([]);

    const fetchDevices = async () => {
        try {
            const response = await api.getAllVirtualDevices();
            setDevices(response.data);
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        fetchDevices();
    }, []);

    return (
        <div>
            { devices.map((x, i) => {
                return <h1>{JSON.stringify(x)}</h1>
            }) }
        </div>
    )
}

export default DevicesPageComponent;