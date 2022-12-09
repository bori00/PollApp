import {HOST} from "../../commons/hosts";
import RestApiClient from "../../commons/api/rest-client"

import * as API_AUTH from "../../commons/authentication/auth-api"

const endpoint = {
    createPoll: '/poll-management/create-poll',
    getUsersPolls: '/poll-management/get-my-polls',
    joinPoll: '/poll-management/join-poll'
};

function getUsersPolls(callback) {

    const request = new Request(HOST.backend_api + endpoint.getUsersPolls, {
        method: 'GET',
        headers: Object.assign({}, {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }, API_AUTH.authHeader()),
    });

    RestApiClient.performRequestWithJsonResponse(request, callback);
}

function createPoll(callback, title, question, options) {
    const body = {"title": title,
        "question": question,
        "options": options};

    const request = new Request(HOST.backend_api + endpoint.createPoll, {
        method: 'POST',
        headers: Object.assign({}, {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }, API_AUTH.authHeader()),
        body: JSON.stringify(body)
    });

    RestApiClient.performRequestWithNoResponse(request, callback);
}

function joinPoll(callback, code) {

    const request = new Request(HOST.backend_api + endpoint.joinPoll, {
        method: 'POST',
        headers: Object.assign({}, {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }, API_AUTH.authHeader()),
        body: JSON.stringify(code)
    });

    RestApiClient.performRequestWithNoResponse(request, callback);
}

export {
    createPoll,
    getUsersPolls,
    joinPoll
};