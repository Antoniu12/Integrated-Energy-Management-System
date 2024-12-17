import React from 'react';
import { Table, Button } from 'reactstrap';

const DeviceTable = ({ devices, onEditDevice, onDeleteDevice }) => (
    <div>
        <Table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>User ID</th>
                    <th>Description</th>
                    <th>Address</th>
                    <th>Energy Consumption</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                {devices.map(device => (
                    <tr key={device.id}>
                        <td>{device.id}</td>
                        <td>{device.userId}</td>
                        <td>{device.description}</td>
                        <td>{device.address}</td>
                        <td>{device.energyConsumption}</td>
                        <td>
                            <Button onClick={() => onEditDevice(device)}>Edit</Button>
                            <Button color="danger" onClick={() => onDeleteDevice(device.id)}>Delete</Button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </Table>
    </div>
);

export default DeviceTable;
