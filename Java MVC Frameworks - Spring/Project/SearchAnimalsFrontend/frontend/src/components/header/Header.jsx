import React from 'react';
import {Nav, Navbar, NavItem} from "react-bootstrap";

import './header.css';

class Header extends React.Component {
    render() {
        return (
            <header>
                <Navbar fixedTop={true}>
                    <Nav>
                        <NavItem eventKey={1} href="/">
                            Home
                        </NavItem>
                        <NavItem eventKey={2} href="/report">
                            Report a pet
                        </NavItem>
                        <NavItem eventKey={3} href="/search">
                            View pets
                        </NavItem>
                        <NavItem eventKey={4} href="/users/login">
                            Login
                        </NavItem>
                    </Nav>
                </Navbar>
            </header>
        )
    }
}

export default Header;