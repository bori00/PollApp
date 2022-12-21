import React, {useEffect, Fragment, useState, Form} from 'react';
import {
    Alert,
    Button,
    Col,
    Modal, ModalBody, ModalFooter, ModalHeader, Row,
    FormGroup, Label, Input, ListGroup, ListGroupItemHeading, ListGroupItem, Badge
} from 'reactstrap';
import Select from 'react-select'

import * as API_POLLS from "./api/poll-management-api"
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import Validate from "../commons/validators/validators";
import * as API_AUTH from "../commons/authentication/auth-api";
import PollCommentsFragment from "./poll-comments-fragment";

function VotePollModal(props) {

    const [error, setError] = useState({ status: 0, errorMessage: null });
    const [poll, setPoll] = useState(props.poll);
    const [success, setSuccess] = useState(0);
    const [savedVote, setSavedVote] = useState(-1);
    const [newlySelectedVote, setNewlySelectedVote] = useState(savedVote);
    const [voted, setVoted] = useState(1);
    const [active, setActive] = useState(props.active);

    useEffect(() => {

        if (voted === 1) {

            const getPollCallback = (result, status, err) => {
                if (result !== null && (status === 200 || status === 201)) {
                    setPoll(result);

                    const getUsersAnswerCallback = (result, status, err) => {
                        if (result !== null && (status === 200 || status === 201)) {
                            setSavedVote(result.optionNr)
                            setNewlySelectedVote(result.optionNr)
                            console.log("Saved vote: ", result.optionNr)

                            console.log("Voted: " + result.optionNr);

                        } else {
                            setError({ status: err.status, errorMessage: err });
                        }
                    };

                    API_POLLS.getUsersAnswerOnPoll(getUsersAnswerCallback, poll.code);
                } else {
                    setError({ status: err.status, errorMessage: err });
                }
            };


            API_POLLS.getPoll(getPollCallback, poll.code);
        }
    }, [voted])

    function closeModal() {
        props.onClose();
    }

    function onNewOptionSelected(option_nr) {
        if (newlySelectedVote !== option_nr) {
            setNewlySelectedVote(option_nr)
            setVoted(0);
            setSuccess(0);
            setError({ status: 0, errorMessage: null });
        } else {
            setNewlySelectedVote(null);
            setVoted(0);
            setSuccess(0);
            setError({ status: 0, errorMessage: null });
        }
    }

    function pollOptionToListGroupItem(option, option_nr) {
        return <ListGroupItem as="li" className="d-flex justify-content-between align-items-start" key={option_nr}
                              action onClick={() => onNewOptionSelected(option_nr)}
                              active={option_nr===newlySelectedVote}
                >
                    <ListGroupItemHeading>{option.option}</ListGroupItemHeading>
                    <Badge bg="primary" pill>
                        {option.nrVotes}
                    </Badge>
                </ListGroupItem>
    }

    function getPollOptions() {
        let option_nr = 0;
        return poll.options.map(option => pollOptionToListGroupItem(option, option_nr++))
    }


    function handleVoteSubmit() {
        const callback = (err) => {
            if (err === null) {
                setSuccess(1);
                setError({status: 0, errorMessage: err});
                setVoted(1);
            } else {
                console.log(error)
                setError({status: err.status, errorMessage: err});
                setSuccess(0);
                setVoted(0);
                setNewlySelectedVote(savedVote);
            }
        };

        if (newlySelectedVote !== null) {
            API_POLLS.castVoteOnPoll(callback, poll.code, newlySelectedVote);
        } else {
            API_POLLS.removeVoteOnPoll(callback, poll.code);
        }
    }

    function handleDiscard() {
        setNewlySelectedVote(savedVote);
    }

    return (
        <Modal isOpen>
            <ModalHeader>Poll "<b>{poll.title}</b>"</ModalHeader>
            <ModalBody>
                {
                    <Fragment>

                        <h4 style={{color: "#106cfc"}}><i>{poll.question}</i></h4>

                        <br/>

                        <h5>Options</h5>
                        <ListGroup as="ol" numbered>
                            {getPollOptions()}
                        </ListGroup>

                        <br/>

                        <Row>
                            <Col sm={{ size: '4', offset: 8 }}>
                                <Button style={{width: "7em", margin: "3px"}} type={"submit"} disabled={false}
                                        onClick={handleVoteSubmit}>Cast Vote</Button>
                            </Col>
                            <Col sm={{ size: '4', offset:  8}}>
                                <Button style={{width: "7em", margin: "3px"}}  type={"submit"} disabled={false} onClick={handleDiscard}>Discard</Button>
                            </Col>
                        </Row>

                    </Fragment>
                }
                {
                    success === 1 &&
                        <Fragment>
                            <Alert color="success">
                                Successful update!
                            </Alert>
                        </Fragment>
                }
                {
                    error.status > 0 &&
                    <APIResponseErrorMessage errorStatus={error.status} error={error.errorMessage} />
                }

                <hr/>
                {
                    <PollCommentsFragment poll={poll} active={active}/>
                }
            </ModalBody>
            <ModalFooter>
                <Button color="white" onClick={closeModal}>Exit</Button>
            </ModalFooter>
        </Modal>

    );

}

export default VotePollModal;
