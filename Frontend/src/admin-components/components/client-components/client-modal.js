import React from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button, FormGroup, Label, Input } from 'reactstrap';

const ClientModal = ({ isOpen, toggle, user, onChange, onSave }) => (
    <Modal isOpen={isOpen} toggle={toggle}>
        <ModalHeader toggle={toggle}>User</ModalHeader>
        <ModalBody>
            <FormGroup>
                <Label>ID</Label>
                <Input type="text" value={user.id} disabled />
            </FormGroup>
            <FormGroup>
                <Label>Name</Label>
                <Input type="text" value={user.name} onChange={e => onChange('name', e.target.value)} />
            </FormGroup>
            <FormGroup>
                <Label>Age</Label>
                <Input type="number" value={user.age} onChange={e => onChange('age', e.target.value)} />
            </FormGroup>
            <FormGroup>
                <Label>Role</Label>
                <Input type="select" value={user.role} onChange={e => onChange('role', e.target.value)}>
                    <option value="">Select a role</option>
                    <option value="admin">admin</option>
                    <option value="client">client</option>
                </Input>
            </FormGroup>
            <FormGroup>
                <Label>Address</Label>
                <Input type="text" value={user.address} onChange={e => onChange('address', e.target.value)} />
            </FormGroup>
            <FormGroup>
                <Label>Password</Label>
                <Input type="password" value={user.password} onChange={e => onChange('password', e.target.value)} />
            </FormGroup>
        </ModalBody>
        <ModalFooter>
            <Button color="primary" onClick={onSave}>Save</Button>
            <Button color="secondary" onClick={toggle}>Cancel</Button>
        </ModalFooter>
    </Modal>
);

export default ClientModal;
