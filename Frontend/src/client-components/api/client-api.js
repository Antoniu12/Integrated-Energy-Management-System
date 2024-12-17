import { HOST } from '../../commons/hosts.js';
import RestApiClient from "../../commons/api/rest-client.js";

const endpoint = {
    person: '/person',
    devices: '/device',
};

function getClientData(userId, token, callback) {
    let request = new Request(HOST.person_api + endpoint.person + `/${userId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        }
    });
    RestApiClient.performRequest(request, callback);
}

function updateClientData(clientData, token, callback) {
    let request = new Request(HOST.person_api + endpoint.person + `/update/${clientData.id}`, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(clientData),
    });
    RestApiClient.performRequest(request, callback);
}

function getClientDevices(userId, token, callback) {
    let request = new Request(HOST.device_api + endpoint.devices + `/getUserDevices/${userId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        }
    });
    RestApiClient.performRequest(request, callback);
}

export {
    getClientData,
    updateClientData,
    getClientDevices,
};
