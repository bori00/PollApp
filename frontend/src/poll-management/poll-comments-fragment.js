import React, {useEffect, Fragment, useState} from 'react';
import {
    ListGroup,
    ListGroupItem,
    ListGroupItemText,
    ListGroupItemHeading,
    Button, FormGroup, Label, Input, Row, Col, Badge
} from 'reactstrap';

import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import de from "react-datepicker";
import CreatePollModal from "./add-poll-modal";
import JoinPollModal from "./join-poll-modal";
import * as API_POLL_MANAGEMENT from "./api/poll-management-api"
import VotePollModal from "./vote-poll-modal"
import Validate from "../commons/validators/validators";
import * as API_POLLS from "./api/poll-management-api";

let formControlsInit = {
    newComment: {
        value: '',
        placeholder: 'Add comment...',
        valid: false,
        touched: false,
        validationRules: {
            isRequired: true,
            minLength: 1,
            maxLength: 100
        },
        errorMessages: []
    }
};

function PollCommentsFragment(props) {

    const [error, setError] = useState({ status: 0, errorMessage: null });
    const [poll, setPoll] = useState(props.poll);
    const [success, setSuccess] = useState(0);
    const [formControls, setFormControls] = useState(formControlsInit);
    const [formIsValid, setFormIsValid] = useState(false);
    const [comments, setComments] = useState(props.poll.recentComments); // last one
    // is the newest
    const [loadedOld, setLoadedOld] = useState(0);
    const [sent, setSent] = useState(0);
    const [active, setActive] = useState(props.active);

    useEffect(() => {
        let commentsList = document.getElementById("commentsList");
        if (commentsList !== null && commentsList !== undefined) {
            commentsList.scrollTop = commentsList.scrollHeight;
        }

        if (sent || !active) {
            console.log("Erasing: ", sent, active)
            let updatedControls = { ...formControls };
            updatedControls.newComment.value = "";
            updatedControls.newComment.valid = false;
            setFormControls(updatedControls);

            const getPollCallback = (result, status, err) => {
                if (result !== null && (status === 200 || status === 201)) {
                    setPoll(result);
                    setComments(result.recentComments);
                    setSent(0);
                    setLoadedOld(0);
                } else {
                    setError({ status: err.status, errorMessage: err });
                }
            };


            API_POLLS.getPoll(getPollCallback, poll.code);
        }
    }, [sent, loadedOld, active])

    function handleFormChange(name, value) {
        let updatedControls = { ...formControls };

        let updatedFormElement = updatedControls[name];

        updatedFormElement.value = value;
        updatedFormElement.touched = true;

        const validation_result = Validate(value, updatedFormElement.validationRules);
        updatedFormElement.valid = validation_result.valid;
        updatedFormElement.errorMessages = validation_result.errorMessages;
        updatedControls[name] = updatedFormElement;

        console.log(updatedControls)

        let formIsValid = true;
        for (let updatedFormElementName in updatedControls) {
            formIsValid = updatedControls[updatedFormElementName].valid && formIsValid;
        }

        setFormControls((formControls) => (updatedControls));
        setFormIsValid((formIsValidPrev) => (formIsValid));
    }

    function handleChange(event) {
        let name = event.target.name;
        let value = event.target.value;

        handleFormChange(name, value);
    }

    function handleSubmit() {
        console.log("Send comment")
        const callback = (error) => {
            if (error === null) {
                setSuccess(1);
                // setFormControls(formControlsInit);
                setSent(1);
            } else {
                console.log(error)
                setError({status: error.status, errorMessage: error});
            }
        };

        API_POLL_MANAGEMENT.addCommentToPoll(callback, poll.code, formControls.newComment.value)
    }

    function pollCommentToListGroupItem(comment, it) {
        return <ListGroupItem key={it}>
            <div style={{color: "#666666"}}><small>By <i>{comment.senderName}</i>, at <i>{comment.localDateTime}</i></small></div>
            {/*<br/>*/}
            <div style={{marginTop: "5px", textAlign: "right"}}>{comment.text}</div>
        </ListGroupItem>
    }

    function getPollComments() {
        let comment_nr = 0;
        return comments.map(comment => pollCommentToListGroupItem(comment, comment_nr++))
    }

    function loadOldComments() {
        let callback = (result, status, err) => {
            if (result !== null && (status === 200 || status === 201)) {
                let oldComments = result;
                oldComments.reverse();
                setComments(oldComments.concat(comments))
                setLoadedOld(1);
            } else {
                setError((error) => ({ status: status, errorMessage: err }));
            }
        };

        API_POLL_MANAGEMENT.getOldCommentsOnPoll(callback, poll.code);
    }

    return (
        <Fragment>
            <h5>Discussion</h5>

            {
                comments.length > 0 &&
                <Fragment>
                    <div  style={{maxHeight: 250, overflowY: "scroll"}} id="commentsList">
                        <ListGroup variant="flush">
                            {
                                loadedOld === 0 &&
                                <ListGroupItem key={-1} style={{textAlign: "center"}} action onClick={loadOldComments}>
                                    <p style={{color: "#106cfc"}}><i>Load More</i></p>
                                </ListGroupItem>
                            }
                            {getPollComments()}
                        </ListGroup>
                    </div>
                </Fragment>
            }

            {
                comments.length === 0 &&
                <Fragment>
                    <p>
                        No discussion yet
                    </p>
                </Fragment>
            }

            <br/>

            <FormGroup id='newComment'>
                {/*<Label for='newCommentField'>Poll joining code: </Label>*/}
                <Input name='newComment' id='newCommentField' placeholder={formControls.newComment.placeholder}
                       onChange={handleChange}
                       value={formControls.newComment.value}
                       touched={formControls.newComment.touched ? 1 : 0}
                       valid={formControls.newComment.valid}
                       required
                       maxLength={90}
                />
                {/*{formControls.newComment.touched && !formControls.newComment.valid &&*/}
                {/*<div className={"error-message"}>{formControls.newComment.errorMessages.join('. ')}</div>}*/}
            </FormGroup>

            <Row>
                <Col sm={{ size: '4', offset: 8 }}>
                    <Button style={{width: "7em", margin: "3px"}} type={"submit"} disabled={!formIsValid} onClick={handleSubmit}>Send</Button>
                </Col>
            </Row>
        </Fragment>
    );

}

export default PollCommentsFragment;
