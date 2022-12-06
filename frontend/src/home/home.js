import React, {useEffect} from 'react';
import { Button, Container, Jumbotron } from 'reactstrap';

import BackgroundImg from '../commons/images/background.png';
import * as API_NOTIFICATIONS from "../commons/sockets/socket-utils";

const backgroundStyle = {
    backgroundPosition: 'center',
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    width: "100%",
    height: "1920px",
    backgroundImage: `url(${BackgroundImg})`
};
const textStyle = { color: 'white', };

function Home() {

    useEffect(() => {
        API_NOTIFICATIONS.setupRoleSpecificNotifications();
    });

    return (
        <div>
            <div className="jumbotron" fluid style={backgroundStyle}>
                <Container fluid>
                    <h1 className="display-1" style={textStyle}>Poll Platform</h1>
                    <p className="lead" style={textStyle}> <b>Where your opinion matters!</b> </p>
                    <hr className="my-2" />
                    <p style={textStyle}> <b>Take decisions easily, by consulting everyone through a poll!</b> </p>
                </Container>
            </div>
        </div>
    );
}


export default Home;