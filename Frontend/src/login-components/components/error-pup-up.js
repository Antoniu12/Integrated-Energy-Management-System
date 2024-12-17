import React from 'react';
import { Modal, ModalBody, ModalHeader, Button } from 'reactstrap';

const ErrorPopup = ({ isOpen, message, onClose }) => (
    <Modal isOpen={isOpen}>
        <ModalHeader>Login Error</ModalHeader>
        <ModalBody>
            <div>{message}</div>
            <Button color="primary" onClick={onClose}>Close</Button>
        </ModalBody>
    </Modal>
);

export default ErrorPopup;
