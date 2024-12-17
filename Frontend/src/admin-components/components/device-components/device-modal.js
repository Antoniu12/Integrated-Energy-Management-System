import React from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button, FormGroup, Label, Input } from 'reactstrap';

const DeviceModal = ({ isOpen, toggle, device, onChange, onSave }) => (
    <Modal isOpen={isOpen} toggle={toggle}>
        <ModalHeader toggle={toggle}>Device</ModalHeader>
        <ModalBody>
            <FormGroup>
                <Label>ID</Label>
                <Input type="text" value={device.id} disabled />
            </FormGroup>
            <FormGroup>
                <Label>User ID</Label>
                <Input type="text" value={device.userId} onChange={e => onChange('userId', e.target.value)} />
            </FormGroup>
            <FormGroup>
                <Label>Description</Label>
                <Input type="text" value={device.description} onChange={e => onChange('description', e.target.value)} />
            </FormGroup>
            <FormGroup>
                <Label>Address</Label>
                <Input type="text" value={device.address} onChange={e => onChange('address', e.target.value)} />
            </FormGroup>
            <FormGroup>
                <Label>Energy Consumption</Label>
                <Input type="number" value={device.energyConsumption} onChange={e => onChange('energyConsumption', e.target.value)} />
            </FormGroup>
        </ModalBody>
        <ModalFooter>
            <Button color="primary" onClick={onSave}>Save</Button>
            <Button color="secondary" onClick={toggle}>Cancel</Button>
        </ModalFooter>
    </Modal>
);

export default DeviceModal;
