import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    person: '/person'
};

function logIn(user, callback) {
    let request = new Request(HOST.person_api + endpoint.person + '/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: user.name,
            password: user.password
        })
    });

    RestApiClient.performRequest(request, callback);
}

export {
    logIn
};
