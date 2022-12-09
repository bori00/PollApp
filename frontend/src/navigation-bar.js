import React, {useState,  Fragment, useLayoutEffect} from 'react'
import { DropdownItem, DropdownMenu, DropdownToggle, Nav, Navbar, NavbarBrand, NavLink, UncontrolledDropdown,  } from 'reactstrap';

import logo from './commons/images/logo.png';
import * as API_AUTH from "./commons/authentication/auth-api";

const textStyle = {
    color: 'white',
    textDecoration: 'none'
};

function NavigationBar() {
    const [userAuthenticated, setUserAuthenticated] = useState(null);

    useLayoutEffect(() => {
        setUserAuthenticated(API_AUTH.isUserAuthenticated());
    });

    return (
        <div>
            <Navbar color="dark" light expand="md">
                <NavbarBrand href="/">
                    <img src={logo} width={"60"}
                        height={"55"} />
                </NavbarBrand>

                <Nav className="mr-auto" navbar>

                    {
                        userAuthenticated &&
                        (
                            <Fragment>
                                <UncontrolledDropdown nav inNavbar>
                                    <DropdownToggle style={textStyle} nav caret>
                                    Menu
                                    </DropdownToggle>
                                    <DropdownMenu right>

                                    <DropdownItem>
                                        <NavLink href="/poll-management">Poll Management</NavLink>
                                    </DropdownItem>

                                    {/*<DropdownItem>*/}
                                    {/*    <NavLink href="/admin-user-management">Users</NavLink>*/}
                                    {/*</DropdownItem>*/}


                                    </DropdownMenu>
                                </UncontrolledDropdown>

                                <UncontrolledDropdown nav inNavbar>
                                    <DropdownToggle style={textStyle} nav caret>
                                    Account
                                    </DropdownToggle>
                                    <DropdownMenu right>
                                    <DropdownItem>
                                    <NavLink href="/logout">Logout</NavLink>
                                    </DropdownItem>
                                    </DropdownMenu>

                                </UncontrolledDropdown>
                            </Fragment>
                        )
                    }

                    {
                        !userAuthenticated &&
                        <UncontrolledDropdown nav inNavbar>
                            <DropdownToggle style={textStyle} nav caret>
                                Account
                            </DropdownToggle>
                            <DropdownMenu right>

                                <DropdownItem>
                                    <NavLink href="/login">Login</NavLink>
                                </DropdownItem>

                                <DropdownItem>
                                    <NavLink href="/register">Register</NavLink>
                                </DropdownItem>


                            </DropdownMenu>

                        </UncontrolledDropdown>
                    }

                </Nav>
            </Navbar>
        </div>
    );
}

export default NavigationBar;
