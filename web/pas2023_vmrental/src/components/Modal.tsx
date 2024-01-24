
const ModalComponent = ({ close, Body }: {close: () => void, Body: React.ElementType}) => {
    return (
        <div id="modal">
            <div id="modal-background" onClick={e => {
                e.stopPropagation();
                close();
            }}>
                <div id="modal-container">
                    <button className="close-button" onClick={close}>X</button>
                    <Body></Body>
                </div>
            </div>
        </div>
    );
};

export default ModalComponent;