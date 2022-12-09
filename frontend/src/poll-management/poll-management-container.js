import React, {useEffect} from 'react';
import {Card, CardHeader, Col, Row } from 'reactstrap';

import {useHistory} from "react-router-dom";
import UsersPollsList from "./polls-list";
import * as API_AUTH from "../commons/authentication/auth-api";

function PollManagementContainer() {

    const history = useHistory();

    useEffect(() => {
        // API_AUTH.guaranteeUserHasRole('ADMIN', history);
    })

    return (
        <div>
            <CardHeader>
                <strong>Poll Management</strong>
            </CardHeader>

            <Card>
                <br />
                <Row>
                    <Col sm={{ size: '8', offset: 1 }}>
                        <UsersPollsList />
                    </Col>
                </Row>

            </Card>

        </div>
    );

}

export default PollManagementContainer;
