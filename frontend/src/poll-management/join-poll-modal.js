import React, {useEffect, Fragment, useState, Form} from 'react';
import {
    Alert,
    Button,
    Col,
    Modal, ModalBody, ModalFooter, ModalHeader, Row,
    FormGroup, Label, Input, ListGroup, ListGroupItemHeading, ListGroupItem, ListGroupItemText
} from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faTrash} from '@fortawesome/free-solid-svg-icons'

import * as API_POLLS from "./api/poll-management-api";
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import styles from "../commons/styles/project-style.css";
import Validate from "../commons/validators/validators";
import * as API_AUTH from "../commons/authentication/auth-api";

let formControlsInit = {
    code: {
        value: null,
        placeholder: 'Poll joining code...',
        valid: false,
        touched: false,
        validationRules: {
            isRequired: true
        },
        errorMessages: []
    }
};

function JoinPollModal(props) {

    const [active, setActive] = useState(props.addPollIntention);
    const [error, setError] = useState({ status: 0, errorMessage: null });
    const [success, setSuccess] = useState(0);
    const [formControls, setFormControls] = useState(formControlsInit);
    const [formIsValid, setFormIsValid] = useState(false);

    useEffect(() => {
        let updatedControls = { ...formControls };

        updatedControls.code.value = null;
        updatedControls.code.valid = false;

        setFormControls(updatedControls);

    }, [active])

    function closeModal() {
        props.onClose();
    }

    function joinPoll(code) {
        const callback = (error) => {
            if (error === null) {
                setSuccess(1);
            } else {
                console.log(error)
                setError({status: error.status, errorMessage: error});
            }
        };

        API_POLLS.joinPoll(callback, code);
    }

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
        joinPoll(formControls.code.value)
    }

    return (
        <Modal isOpen>
            <ModalHeader>Join existing Poll</ModalHeader>
            <ModalBody>
                { success === 0 && error.status === 0 &&
                <Fragment>
                    <FormGroup id='code'>
                        <Label for='codeField'>Poll joining code: </Label>
                        <Input name='code' id='codeField' placeholder={formControls.code.placeholder}
                               onChange={handleChange}
                               defaultValue={formControls.code.value}
                               touched={formControls.code.touched ? 1 : 0}
                               valid={formControls.code.valid}
                               required
                        />
                        {formControls.code.touched && !formControls.code.valid &&
                        <div className={"error-message"}>{formControls.code.errorMessages.join('. ')}</div>}
                    </FormGroup>

                    <Row>
                        <Col sm={{ size: '4', offset: 8 }}>
                            <Button type={"submit"} disabled={!formIsValid} onClick={handleSubmit}>Join Poll</Button>
                        </Col>
                    </Row>

                </Fragment>
                }
                {
                    success === 1 &&
                    <Fragment>
                        <Alert color="success">
                            Successfully joined!
                        </Alert>
                    </Fragment>
                }
                {
                    error.status > 0 &&
                    <APIResponseErrorMessage errorStatus={error.status} error={error.errorMessage} />
                }
            </ModalBody>
            <ModalFooter>
                <Button color="white" onClick={closeModal}>Exit</Button>
            </ModalFooter>
        </Modal>

    );

}

export default JoinPollModal;
