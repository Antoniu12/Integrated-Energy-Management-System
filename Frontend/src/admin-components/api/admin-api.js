import { HOST } from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";

const endpoint = {
    person: '/person',
    devices: '/device',
};

// CRUD for users
function getPersons(token, callback) {
    let request = new Request(HOST.person_api + endpoint.person + "/Persons", {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        }
    });
    RestApiClient.performRequest(request, callback);
}

function getPersonById(id, token, callback) {
    let request = new Request(HOST.person_api + endpoint.person + `/${id}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        }
    });
    RestApiClient.performRequest(request, callback);
}

function postPerson(user, token, callback) {
    let request = new Request(HOST.person_api + endpoint.person + "/insert", {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
    });
    RestApiClient.performRequest(request, callback);
}

function updatePerson(user, token, callback) {
    let request = new Request(HOST.person_api + endpoint.person + `/update/${user.id}`, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
    });
    RestApiClient.performRequest(request, callback);
}

function deletePerson(id, token, callback) {
    let request = new Request(HOST.person_api + endpoint.person + `/delete/${id}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        }
    });
    RestApiClient.performRequest(request, callback);
}

// CRUD for devices
function getDevices(callback) {
    let request = new Request(HOST.device_api + endpoint.devices +"/Devices", {
        method: 'GET',
    });
    RestApiClient.performRequest(request, callback);
}

function getDeviceById(id, callback) {
    let request = new Request(HOST.device_api + endpoint.devices + `/${id}`, {
        method: 'GET',
    });
    RestApiClient.performRequest(request, callback);
}

function postDevice(device, callback) {
    let request = new Request(HOST.device_api + endpoint.devices +"/insert", {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(device),
    });
    RestApiClient.performRequest(request, callback);
}

function updateDevice(device, callback) {
    let request = new Request(HOST.device_api + endpoint.devices + `/update/${device.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(device),
    });
    RestApiClient.performRequest(request, callback);
}

function deleteDevice(id, callback) {
    let request = new Request(HOST.device_api + endpoint.devices + `/delete/${id}`, {
        method: 'DELETE',
    });
    RestApiClient.performRequest(request, callback);
}

// Get devices by user ID
function getDevicesByUserId(userId, token, callback) {
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
    getPersons,
    getPersonById,
    postPerson,
    updatePerson,
    deletePerson,
    getDevices,
    getDeviceById,
    postDevice,
    updateDevice,
    deleteDevice,
    getDevicesByUserId
};
