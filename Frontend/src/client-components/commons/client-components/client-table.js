import React from 'react';
import { Table, Button } from 'reactstrap';

const ClientTable = ({ client, openModal }) => (
    <div>
        <h2>Your Information</h2>
        <Table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Age</th>
                    <th>Address</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>{client.id}</td>
                    <td>{client.name}</td>
                    <td>{client.age}</td>
                    <td>{client.address}</td>
                    <td>{client.role}</td>
                    <td>
                        <Button onClick={openModal}>Edit</Button>
                    </td>
                </tr>
            </tbody>
        </Table>
    </div>
);

export default ClientTable;
