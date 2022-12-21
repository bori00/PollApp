import {HOST} from "../../commons/hosts";
import RestApiClient from "../../commons/api/rest-client"

import * as API_AUTH from "../../commons/authentication/auth-api"

const endpoint = {
    createPoll: '/poll-management/create-poll',
    getUsersPolls: '/poll-management/get-my-polls',
    joinPoll: '/poll-management/join-poll',
    getUsersAnswerOnPoll: '/poll-answering/get-my-answer-for-poll',
    castVoteOnPoll: '/poll-answering/cast-vote-for-poll',
    getPoll: '/poll-answering/get-poll',
    removeVoteOnPoll: '/poll-answering/remove-vote-for-poll',
    addCommentForPoll: '/poll-discussion/add-comment-to-poll',
    getOldPollComments: '/poll-discussion/get-older-poll-comments'
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

function getPoll(callback, pollJoinCode) {

    var url = new URL(HOST.backend_api + endpoint.getPoll);
    url.searchParams.append('pollJoiningCode', pollJoinCode);

    const request = new Request(url, {
        method: 'GET',
        headers: Object.assign({}, {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }, API_AUTH.authHeader()),
    });

    RestApiClient.performRequestWithJsonResponse(request, callback);
}

function getUsersAnswerOnPoll(callback, pollJoinCode) {

    var url = new URL(HOST.backend_api + endpoint.getUsersAnswerOnPoll);
    url.searchParams.append('pollJoiningCode', pollJoinCode);

    const request = new Request(url, {
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

function castVoteOnPoll(callback, pollJoinCode, selectedOptionNr) {
    const body = {"pollJoinCode": pollJoinCode,
        "selectedOptionIndex": selectedOptionNr};

    const request = new Request(HOST.backend_api + endpoint.castVoteOnPoll, {
        method: 'POST',
        headers: Object.assign({}, {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }, API_AUTH.authHeader()),
        body: JSON.stringify(body)
    });

    RestApiClient.performRequestWithNoResponse(request, callback);
}

function removeVoteOnPoll(callback, pollJoinCode) {

    const request = new Request(HOST.backend_api + endpoint.removeVoteOnPoll, {
        method: 'POST',
        headers: Object.assign({}, {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }, API_AUTH.authHeader()),
        body: JSON.stringify(pollJoinCode)
    });

    RestApiClient.performRequestWithNoResponse(request, callback);
}

function addCommentToPoll(callback, pollJoinCode, commentText) {

    const body = {"pollJoinCode": pollJoinCode,
        "commentText": commentText};

    const request = new Request(HOST.backend_api + endpoint.addCommentForPoll, {
        method: 'POST',
        headers: Object.assign({}, {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }, API_AUTH.authHeader()),
        body: JSON.stringify(body)
    });

    RestApiClient.performRequestWithNoResponse(request, callback);
}

function getOldCommentsOnPoll(callback, pollJoinCode) {

    var url = new URL(HOST.backend_api + endpoint.getOldPollComments);
    url.searchParams.append('pollJoinCode', pollJoinCode);

    const request = new Request(url, {
        method: 'GET',
        headers: Object.assign({}, {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }, API_AUTH.authHeader()),
    });

    RestApiClient.performRequestWithJsonResponse(request, callback);
}


export {
    createPoll,
    getUsersPolls,
    joinPoll,
    getUsersAnswerOnPoll,
    castVoteOnPoll,
    getPoll,
    removeVoteOnPoll,
    addCommentToPoll,
    getOldCommentsOnPoll
};