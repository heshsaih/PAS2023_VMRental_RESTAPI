import { MouseEventHandler } from "react";

const ModalComponent = ({ close, Body }: {close: MouseEventHandler, Body: React.ElementType}) => {
    return (
        <div id="modal">
            <div id="modal-background">
                <div id="modal-container">
                    <button className="close-button" onClick={close}>X</button>
                    <Body></Body>
                </div>
            </div>
        </div>
    );
};

export default ModalComponent;