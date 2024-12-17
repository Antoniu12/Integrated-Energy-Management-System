import React from 'react';
import { Table, Button } from 'reactstrap';

const ClientTable = ({ users, onEditUser, onDeleteUser, onViewDevices, onSelectUserForChat }) => (
    <div>
        <Table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Age</th>
                    <th>Role</th>
                    <th>Address</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                {users.map(user => (
                    <tr key={user.id}>
                        <td>{user.id}</td>
                        <td>{user.name}</td>
                        <td>{user.age}</td>
                        <td>{user.role}</td>
                        <td>{user.address}</td>
                        <td>
                            <Button onClick={() => onViewDevices(user.id)}>View Devices</Button>
                            <Button onClick={() => onEditUser(user)}>Edit</Button>
                            <Button color="danger" onClick={() => onDeleteUser(user.id)}>Delete</Button>
                            <Button color="primary" onClick={() => onSelectUserForChat(user.id)}>
                                Chat
                            </Button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </Table>
    </div>
);

export default ClientTable;
