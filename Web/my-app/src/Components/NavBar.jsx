import React from 'react'
import {Navbar, Container, Nav, NavDropdown, Alert} from 'react-bootstrap'
import {Link, Outlet} from 'react-router-dom'
import styles from './NavBar.module.css'
import { useNavigate } from 'react-router-dom'

export default function NavBar() {

    const navigate = useNavigate()
    const goToLogin = () => navigate('/Login')

    const LoggedOutAlert = () => {
        <Alert variant="success" style={{ width: "42rem" }}>
            <Alert.Heading>
                Succesfully Logged Out
            </Alert.Heading>
        </Alert>
    }

    const logOut = () => {
        localStorage.removeItem('token')
        goToLogin()
    }

    

    let userBar
    if (localStorage.getItem('token')){
        userBar = (
        <NavDropdown title="My User" id="basic-nav-dropdown">
        <NavDropdown.Item as={Link} to="/User">Go to profile</NavDropdown.Item>
        <NavDropdown.Item onClick={logOut}>Log Out</NavDropdown.Item>
    </NavDropdown>)
    } else {
        userBar = (
        <NavDropdown title="User" id="basic-nav-dropdown">
        <NavDropdown.Item as={Link} to="/Login">Login</NavDropdown.Item>
        <NavDropdown.Item as={Link} to="/Register">Register</NavDropdown.Item>
    </NavDropdown>)
    }
    

  return (
    <>
    <Navbar className={styles.NavBg} expand="lg">
        <Container  >
            <Navbar.Brand as={Link} to="/"> 
                <img src="https://www.rottentomatoes.com/assets/pizza-pie/images/rtlogo.9b892cff3fd.png" width='100' alt='Rotten Tomatoes' />
            </Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
                <Nav.Link as={Link} to="/">Home</Nav.Link>
                <Nav.Link as={Link} to="/Categories">Categories</Nav.Link>
                <Nav.Link as={Link} to="/Content/Top">Top 10</Nav.Link>
                <Nav.Link as={Link} to="/Content/Latest">Latest Content</Nav.Link>
                {userBar}
            </Nav>
            </Navbar.Collapse>
        </Container>
    </Navbar>
    <Outlet></Outlet>
    </>
  )
}
