import {useEffect, useState} from "react";
import {api} from "../../api/api.ts";
import {VirtualDeviceType} from "../../types/VirtualDevice.ts";

const DevicesPageComponent = () => {
    const [devices, setDevices] = useState<VirtualDeviceType[]>([]);

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
            { devices.map((x, _i) => {
                return <h3>{JSON.stringify(x)}</h3>
            }) }
        </div>
    )
}

export default DevicesPageComponent;