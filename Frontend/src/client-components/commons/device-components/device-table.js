import React from 'react';
import { Table } from 'reactstrap';

const DeviceTable = ({devices}) => (
    <div>
        <h2>Your Devices</h2>
                <Table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Description</th>
                            <th>Address</th>
                            <th>Energy Consumption</th>
                        </tr>
                    </thead>
                    <tbody>
                        {devices.map(device => (
                            <tr key={device.id}>
                                <td>{device.id}</td>
                                <td>{device.description}</td>
                                <td>{device.address}</td>
                                <td>{device.energyConsumption}</td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
    </div>
);

export default DeviceTable;
