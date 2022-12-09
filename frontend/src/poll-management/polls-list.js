import React, {useEffect, Fragment, useState} from 'react';
import {
    ListGroup,
    ListGroupItem,
    ListGroupItemText,
    ListGroupItemHeading,
    Button
} from 'reactstrap';

import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import de from "react-datepicker";
import CreatePollModal from "./add-poll-modal";
import JoinPollModal from "./join-poll-modal";
import * as API_POLL_MANAGEMENT from "./api/poll-management-api"

function UsersPollsList() {

    const [error, setError] = useState({ status: 0, errorMessage: null });
    const [polls, setPolls] = useState([]);
    const [selectedPoll, setSelectedPoll] = useState(null);
    const [addPollIntention, setAddPollIntention] = useState(0);
    const [joinPollIntention, setJoinPollIntention] = useState(0);

    function pollSelected(poll) {
        console.log("Selected poll: ", poll)
        setSelectedPoll(poll)
    }

    function onAddPollIntention() {
        setAddPollIntention(1);
    }

    function onJoinPollIntention() {
        setJoinPollIntention(1);
    }

    useEffect(() => {
        API_POLL_MANAGEMENT.getUsersPolls((result, status, err) => {
            if (result !== null && (status === 200 || status === 201)) {
                let newPolls = [];
                result.forEach(poll => {
                    newPolls.push(
                        <ListGroupItem key={poll.code} action onClick={() => pollSelected(poll)}>
                            <ListGroupItemHeading>{poll.title}</ListGroupItemHeading>
                            <i>Admin: {poll.userName}</i>
                            <br/>
                            <i>Joinng Code: {poll.code}</i>
                        </ListGroupItem>
                    )
                });

                setPolls(newPolls);
            } else {
                setError((error) => ({ status: status, errorMessage: err }));
            }
        })
    }, [selectedPoll, addPollIntention, joinPollIntention])

    return (
        <Fragment>
            <Button color="info" style={{marginRight: 1 + 'em'}} onClick={() => onAddPollIntention()}>New Poll</Button>

            <Button color="info" onClick={() => onJoinPollIntention()}>Join Poll</Button>

            <hr></hr>

            <h3>My Polls</h3>
            <ListGroup>
                {polls}
            </ListGroup>

            {
                error.status > 0 &&
                <APIResponseErrorMessage errorStatus={error.status} error={error.errorMessage} />
            }

            {/*{*/}
            {/*    selectedDevice !== null &&*/}
            {/*    <ManagePollModal device={selectedPoll} onClose={() => {setSelectedPoll(null)}}/>*/}
            {/*}*/}

            {
                addPollIntention === 1 &&
                <CreatePollModal onClose={() => {setAddPollIntention(0)}}/>
            }

            {
                joinPollIntention === 1 &&
                <JoinPollModal onClose={() => {setJoinPollIntention(0)}}/>
            }
        </Fragment>
    );

}

export default UsersPollsList;
