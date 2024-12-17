import React from 'react';
import logo from './commons/images/icon.png';
import {
    Nav,
    Navbar,
    NavbarBrand,
    Button,
    NavItem
} from 'reactstrap';
import { withRouter } from 'react-router-dom'; 

class NavigationBar extends React.Component {
    constructor(props) {
        super(props);
        this.handleSignOut = this.handleSignOut.bind(this);
    }

    handleSignOut() {
        localStorage.clear()
        this.props.history.push('/');  
    }

    render() {
        const { location } = this.props;  
        const isLogedIn = location.pathname === '/admin' || location.pathname === '/client';  

        return (
            <div>
                <Navbar color="dark" light expand="md">
                    <NavbarBrand href="/">
                        <img src={logo} width={"50"} height={"35"} alt="logo" />
                    </NavbarBrand>
                    <Nav className="ml-auto" navbar>
                        <NavItem>
                            {}
                            {isLogedIn ? (
                                <Button color="danger" onClick={this.handleSignOut}>Sign Out</Button>
                            ) : (
                                <Button color="primary" href="/login">Login</Button>
                            )}
                        </NavItem>
                    </Nav>
                </Navbar>
            </div>
        );
    }
}

export default withRouter(NavigationBar);
