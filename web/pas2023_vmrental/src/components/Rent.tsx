import { useState } from "react";
import { ParsedRentType, RentType } from "../types/Rent";
import ModalComponent from "./Modal";
import { api } from "../api/api";

const RentComponent = ({ rent, getRents }: { rent: RentType, getRents: () => void }) => {
    const [displayRentsDetails, setDisplayRentsDetails] = useState(false);
    const openModal = () => setDisplayRentsDetails(true);
    const closeModal = () => setDisplayRentsDetails(false);


    const RentsDetailsBody = () => {
        const endRent = async () => {
            if (confirm("Are you sure you want to proceed?")) {
                const response = await api.deleteRent(rent.rentId);
                    if (response.status === 200) {
                        alert("Rent has been ended successfully!");
                        closeModal();
                        await getRents();
                    } else {
                        alert(`Rent's cancellation failed with status code ${response.request.status} and message:\n\n${response.request.responseText}`);
                    }
            }
        }
        
        return (
            <div id="modal-body">
                <h1>Rent details</h1>
                <div id="rent-buttons">
                    <button className="button" onClick={endRent}>End rent</button>
                </div>
                <div className="details">
                    <div className="value">
                        <h3>Rent ID</h3>
                    </div>
                    <div className="value">
                        <p>{rent.rentId}</p>
                    </div>
                    <div className="value">
                        <h3>Rent start date</h3>
                    </div>
                    <div className="value">
                        <p>{rent.startLocalDateTime}</p>
                    </div>
                    <div className="value">
                        <h3>Rent end date</h3>
                    </div>
                    <div className="value">
                        <p>{rent.endLocalDateTime}</p>
                    </div>
                    <div className="value">
                        <h3>Renter username</h3>
                    </div>
                    <div className="value">
                        <p>{rent.renterUsername}</p>
                    </div>
                    <div className="value">
                        <h3>Renter ID</h3>
                    </div>
                    <div className="value">
                        <p>{rent.userId}</p>
                    </div>
                    <div className="value">
                        <h3>Rented device ID</h3>
                    </div>
                    <div className="value">
                        <p>{rent.virtualDeviceId}</p>
                    </div>
                    <div className="value">
                        <h3>Rented device type</h3>
                    </div>
                    <div className="value">
                        <p>{rent.virtualDeviceType}</p>
                    </div>
                </div>
            </div>
        );
    };

    return (
        <div className="list-element" onClick={openModal}>
            <h2>{rent.rentId}</h2>
            <h3>{rent.renterUsername}</h3>
            <p>{rent.virtualDeviceType}</p>
            { (new Date(rent.endLocalDateTime) - new Date()) < 0 && <p style={{color: "red"}}>Ended</p> }
            { (new Date(rent.endLocalDateTime) - new Date()) >= 0 && (new Date(rent.startLocalDateTime) - new Date()) <= 0 && <p style={{color: "green"}}>Active</p> }
            { (new Date(rent.startLocalDateTime) - new Date()) > 0 &&  <p style={{color: "blue"}}>Pending</p> }
            { displayRentsDetails && <ModalComponent close={e => {
                e.stopPropagation();
                closeModal();
            }} Body={RentsDetailsBody}></ModalComponent> }
        </div>
    )
};

export default RentComponent;