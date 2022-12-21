import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import NavigationBar from './navigation-bar';
import Home from './home/home';
import LoginContainer from "./authentication/login-container";
import RegistrationContainer from "./registration/registration-container";
import ErrorPage from './commons/errorhandling/error-page';
import LogoutFunction from "./authentication/logout-container";
import styles from './commons/styles/project-style.css';
import PollManagementContainer from "./poll-management/poll-management-container";

function App() {
    return (
        <div className={styles.back}>
            <Router>
                <div>
                    <NavigationBar />
                    <Switch>

                        <Route
                            exact
                            path='/'
                            render={() => <Home />}
                        />

                        <Route
                            exact
                            path='/login'
                            render={() => <LoginContainer />}
                        />

                        <Route
                            exact
                            path='/logout'
                            render={() => <LogoutFunction />}
                        />

                        <Route
                            exact
                            path='/register'
                            render={() => <RegistrationContainer />}
                        />

                        <Route
                            exact
                            path='/poll-management'
                            render={() => <PollManagementContainer />}
                        />

                        {/*Error*/}
                        <Route
                            exact
                            path='/error'
                            render={() => <ErrorPage />}
                        />

                        <Route render={() => <ErrorPage />} />
                    </Switch>
                </div>
            </Router>
        </div>
    );
}

export default App;
