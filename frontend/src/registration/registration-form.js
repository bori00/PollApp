import React, { useState, useEffect} from 'react';
import { Col, Row } from "reactstrap";
import { FormGroup, Input, Label, Alert } from 'reactstrap';
import Button from "react-bootstrap/Button";
import { useHistory } from "react-router-dom";
import Select from 'react-select'

import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import * as API_REGISTER from "./api/registration-api";
import * as API_AUTH from "../commons/authentication/auth-api";
import Validate from "../commons/validators/validators";

function getValueLabelDictionary(v) {
    return { value: v, label: v }
}

const formControlsInit = {
    userName: {
        value: '',
        placeholder: 'Username...',
        valid: false,
        touched: false,
        validationRules: {
            isRequired: true,
            minLength: 3
        },
        errorMessages: []
    },
    password: {
        value: '',
        placeholder: 'Password...',
        valid: false,
        touched: false,
        validationRules: {
            isRequired: true,
            minLength: 3
        },
        errorMessages: []
    }
};

function RegistrationForm() {
    const [error, setError] = useState({ status: 0, errorMessage: null });
    const [success, setSuccess] = useState(0);
    const [userIsAdmin, setUserIsAdmin] = useState(false);
    const [formIsValid, setFormIsValid] = useState(false);
    const [formControls, setFormControls] = useState(formControlsInit);
    const history = useHistory();

    useEffect(() => {
        setUserIsAdmin(API_AUTH.getCurrentUserRole() === 'ADMIN');
    }, [error, success])

    function handleFormChange(name, value) {
        let updatedControls = { ...formControls };

        let updatedFormElement = updatedControls[name];

        updatedFormElement.value = value;
        updatedFormElement.touched = true;

        const validation_result = Validate(value, updatedFormElement.validationRules);
        updatedFormElement.valid = validation_result.valid;
        updatedFormElement.errorMessages = validation_result.errorMessages;
        updatedControls[name] = updatedFormElement;

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

    function handleUserTypeChange(e) {
        let name = "userType";
        let value = e.value;

        handleFormChange(name, value);
    }

    function register(userName, password) {
        const callback = (err) => {
            if (err === null) {
                setSuccess(1);
                setError({ status: 0, errorMessage: ""});
            } else {
                setSuccess(0);
                setError({status: err.status, errorMessage: err});
            }
        }

        API_REGISTER.register(callback, userName, password);
    }

    function handleSubmit() {
        register(formControls.userName.value,
            formControls.password.value);
    }

    return (
        <div>
            <FormGroup id='userName'>
                <Label for='userNameField'>Username: </Label>
                <Input name='userName' id='userNameField' placeholder={formControls.userName.placeholder}
                       onChange={handleChange}
                       defaultValue={formControls.userName.value}
                       touched={formControls.userName.touched ? 1 : 0}
                       valid={formControls.userName.valid}
                       required
                />
                {formControls.userName.touched && !formControls.userName.valid &&
                <div className={"error-message"}>{formControls.userName.errorMessages.join('. ')}</div>}
            </FormGroup>

            <FormGroup id='password'>
                <Label for='Field'>Password: </Label>
                <Input name='password' id='passwordField' placeholder={formControls.password.placeholder}
                       onChange={handleChange}
                       type="password"
                       defaultValue={formControls.password.value}
                       touched={formControls.password.touched ? 1 : 0}
                       valid={formControls.password.valid}
                       required
                />
                {formControls.password.touched && !formControls.password.valid &&
                <div className={"error-message"}>{formControls.password.errorMessages.join('. ')}</div>}
            </FormGroup>

            <Row>
                <Col sm={{ size: '4', offset: 8 }}>
                    <Button type={"submit"} disabled={!formIsValid} onClick={handleSubmit}> Submit </Button>
                </Col>
            </Row>

            {
                error.status > 0 &&
                <APIResponseErrorMessage errorStatus={error.status} error={error.errorMessage} />
            }

            {
                success === 1 &&
                <Alert color="success">
                    Successful registration! Please go ahead and log in into your account!
                </Alert>
            }
        </div>
    );
}

export default RegistrationForm;
