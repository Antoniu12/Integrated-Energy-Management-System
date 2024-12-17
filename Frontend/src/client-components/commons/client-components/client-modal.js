import React from 'react';
import { Modal, ModalBody, ModalHeader, ModalFooter, Button, FormGroup, Input, Label } from 'reactstrap';

const ClientModal = ({ isOpen, client, toggle, handleChange, handleSave }) => (
    <Modal isOpen={isOpen} toggle={toggle}>
        <ModalHeader toggle={toggle}>Edit Information</ModalHeader>
        <ModalBody>
            <FormGroup>
                <Label>Name</Label>
                <Input type="text" value={client.name} onChange={e => handleChange('name', e.target.value)} />
            </FormGroup>
            <FormGroup>
                <Label>Age</Label>
                <Input type="number" value={client.age} onChange={e => handleChange('age', e.target.value)} />
            </FormGroup>
            <FormGroup>
                <Label>Address</Label>
                <Input type="text" value={client.address} onChange={e => handleChange('address', e.target.value)} />
            </FormGroup>
            <FormGroup>
                <Label>Password</Label>
                <Input type="password" value={client.password} onChange={e => handleChange('password', e.target.value)} />
            </FormGroup>
        </ModalBody>
        <ModalFooter>
            <Button color="primary" onClick={handleSave}>Save</Button>
            <Button color="secondary" onClick={toggle}>Cancel</Button>
        </ModalFooter>
    </Modal>
);

export default ClientModal;
