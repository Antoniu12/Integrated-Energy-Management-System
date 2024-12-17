import React from 'react';
import { Button, FormGroup, Input, Label, Modal, ModalBody, ModalHeader } from 'reactstrap';

const LoginModal = ({ isOpen, onLogin, onClose, error }) => {
    const [name, setName] = React.useState('');
    const [password, setPassword] = React.useState('');

    const handleChange = (event) => {
        const { name, value } = event.target;
        if (name === "name") {
            setName(value);
        } else if (name === "password") {
            setPassword(value);
        }
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        onLogin({ name, password });
    };

    return (
        <Modal isOpen={isOpen}>
            <ModalHeader>Login</ModalHeader>
            <ModalBody>
                <FormGroup>
                    <Label for="name">Name:</Label>
                    <Input
                        type="text"
                        name="name"
                        id="name"
                        placeholder="Insert your username"
                        value={name}
                        onChange={handleChange}
                        required
                    />
                </FormGroup>

                <FormGroup>
                    <Label for="password">Password:</Label>
                    <Input
                        type="password"
                        name="password"
                        id="password"
                        placeholder="Insert your password."
                        value={password}
                        onChange={handleChange}
                        required
                    />
                </FormGroup>

                {error && (
                    <div className="error-message"> {error}</div>
                )}

                <Button color="primary" onClick={handleSubmit}>
                    Login
                </Button>
                <Button color="secondary" onClick={onClose}>
                    Cancel
                </Button>
            </ModalBody>
        </Modal>
    );
};

export default LoginModal;
