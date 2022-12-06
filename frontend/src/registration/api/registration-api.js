import {HOST} from "../../commons/hosts";
import RestApiClient from "../../commons/api/rest-client"

const endpoint = {
    register: '/auth/register'
};

function register(callback, username, password) {

    const body = {"userName": username,
        "password": password};

    const request = new Request(HOST.backend_api + endpoint.register, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body)
    });

    RestApiClient.performRequestWithNoResponse(request, callback);
}

export {
    register
};