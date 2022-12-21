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

import * as API_DEVICES from "./api/poll-management-api";
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import styles from "../commons/styles/project-style.css";
import Validate from "../commons/validators/validators";
import * as API_AUTH from "../commons/authentication/auth-api";

let formControlsInit = {
    title: {
        value: '',
        placeholder: 'Poll title...',
        valid: false,
        touched: false,
        validationRules: {
            isRequired: true,
            minLength: 3,
            maxLength: 60
        },
        errorMessages: []
    },
    question: {
        value: '',
        placeholder: 'Poll question...',
        valid: false,
        touched: false,
        validationRules: {
            isRequired: true,
            minLength: 3,
            maxLength: 300
        },
        errorMessages: []
    },
    newOption: {
        value: '',
        placeholder: 'Next poll option...',
        valid: false,
        touched: false,
        validationRules: {
            minLength: 3,
            maxLength: 300
        },
        errorMessages: []
    },
    options: {
        value: [],
        valid: false,
    },
};

function CreatePollModal(props) {

    const [active, setActive] = useState(props.addPollIntention);
    const [error, setError] = useState({ status: 0, errorMessage: null });
    const [success, setSuccess] = useState(0);
    const [formControls, setFormControls] = useState(formControlsInit);
    const [formIsValid, setFormIsValid] = useState(false);

    useEffect(() => {
        let updatedControls = { ...formControls };

        updatedControls.title.value = "";
        updatedControls.title.valid = false;
        updatedControls.question.value = "";
        updatedControls.question.valid = false;
        updatedControls.options.value = [];
        updatedControls.options.valid = false;
        updatedControls.newOption.value = "";
        updatedControls.newOption.valid = false;

        setFormControls(updatedControls);

    }, [active])

    function closeModal() {
        props.onClose();
    }

    function createPoll(title, question, options) {
        const callback = (error) => {
            if (error === null) {
                setSuccess(1);
            } else {
                console.log(error)
                setError({status: error.status, errorMessage: error});
            }
        };

        API_DEVICES.createPoll(callback, title, question, options);
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

    function onAddNewOption() {
        let value = formControls.newOption.value;

        if (!formControls.options.value.includes(value)) {

            console.log("Adding")
            let updatedControls = {...formControls};
            updatedControls.options.value.push(value)
            updatedControls.options.valid = true;
            updatedControls.newOption.value = '';

            setFormControls(updatedControls)
        } else {
            window.alert("This option already exists");
        }
    }

    function handleSubmit() {
        createPoll(formControls.title.value,
            formControls.question.value,
            formControls.options.value)
    }

    function deleteOption(option) {
        let updatedControls = { ...formControls };

        updatedControls.options.value.splice(updatedControls.options.value.indexOf(option), 1)

        setFormControls(updatedControls)
    }

    function optionToListElement(option) {
        console.log("Converting...")
        return <ListGroupItem as="li"
                              // action onClick={() => deviceSelected(device)}
                >
                 {option}
                 <Button color="light" size="sm" className="btn-light" style={{float: 'right'}} onClick={() => {deleteOption(option)}}>Delete</Button>
            </ListGroupItem>
    }

    function getSelectedOptions() {
        return formControls.options.value.map(option => optionToListElement(option))
    }

    return (
        <Modal isOpen>
            <ModalHeader>Create new Poll</ModalHeader>
            <ModalBody>
                { success === 0 && error.status === 0 &&
                <Fragment>
                    <FormGroup id='title'>
                        <Label for='titleField'>Poll title: </Label>
                        <Input name='title' id='titleField' placeholder={formControls.title.placeholder}
                               onChange={handleChange}
                               defaultValue={formControls.title.value}
                               touched={formControls.title.touched ? 1 : 0}
                               valid={formControls.title.valid}
                               required
                        />
                        {formControls.title.touched && !formControls.title.valid &&
                        <div className={"error-message"}>{formControls.title.errorMessages.join('. ')}</div>}
                    </FormGroup>

                    <FormGroup id='question'>
                        <Label for='questionField'>Poll question: </Label>
                        <Input name='question' id='questionField' placeholder={formControls.question.placeholder}
                               onChange={handleChange}
                               defaultValue={formControls.question.value}
                               touched={formControls.question.touched ? 1 : 0}
                               valid={formControls.question.valid}
                               required
                        />
                        {formControls.question.touched && !formControls.question.valid &&
                        <div className={"error-message"}>{formControls.question.errorMessages.join('. ')}</div>}
                    </FormGroup>

                    <FormGroup id='newOption'>
                        <Label for='newOptionField'>New option: </Label>
                        <Input name='newOption' id='newOptionField' placeholder={formControls.newOption.placeholder}
                               onChange={handleChange}
                               value={formControls.newOption.value}
                               touched={formControls.newOption.touched ? 1 : 0}
                               valid={formControls.newOption.valid}
                               required
                        />
                        {formControls.newOption.touched && !formControls.newOption.valid &&
                        <div className={"error-message"}>{formControls.newOption.errorMessages.join('. ')}</div>}

                        <Button type={"button"} disabled={!formControls.newOption.valid} onClick={onAddNewOption}>Add Option</Button>
                    </FormGroup>

                    <ListGroup as="lo" numbered style={{maxHeight: 250, overflowY: "scroll"}}>
                        {/*<h6>Options:</h6>*/}
                        {getSelectedOptions()}
                    </ListGroup>

                    <hr></hr>

                    <Row>
                        <Col sm={{ size: '4', offset: 8 }}>
                            <Button type={"submit"} disabled={!formIsValid} onClick={handleSubmit}>Save Poll</Button>
                        </Col>
                    </Row>

                </Fragment>
                }
                {
                    success === 1 &&
                    <Fragment>
                        <Alert color="success">
                            Successfully saved!
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

export default CreatePollModal;
