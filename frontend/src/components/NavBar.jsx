import React, { useContext } from 'react';
import { Container, Nav, Navbar, NavDropdown } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { UserContext } from '../context/ContextProvider';

export const NavBar = () => {
  const navigate = useNavigate();
  const userContext = useContext(UserContext);

  const handleLogout = () => {
    userContext.setUser((state) => {
      return {
        ...state,
        isAuthenticated: false,
        email: '',
        username: '',
        token: undefined
      };
    });
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <Navbar bg='light' expand='md' className="shadow-sm">
      <Container fluid>
        <Navbar.Toggle aria-controls="navbarSupportedContent" />
        <Navbar.Collapse id="navbarSupportedContent">
          <Nav className="navbar-nav me-auto mb-2 mb-lg-0">
            <Nav.Item className="nav-item dropdown">
              <NavDropdown title={userContext.username} id="basic-nav-dropdown">
                <Link className="dropdown-item" to="/edit-profile/1">Edit Profile</Link>
                <NavDropdown.Item onClick={handleLogout}> Log Out </NavDropdown.Item>
              </NavDropdown>
            </Nav.Item>
            <Nav.Item className="nav-item">
              <Link className="nav-link" to="/projects/create-project">Create Project</Link>
            </Nav.Item>
            <Nav.Item className="nav-item">
              <Link className="nav-link" to="/">My Projects</Link>
            </Nav.Item>
          </Nav>
          {/* <Form className="d-flex" role="search">
            <Form.Control
              type='search'
              placeholder='Search...'
              aria-label='Search'
              className='me-2'
            />
            <Button variant='dark' type="submit"> Search</Button>
          </Form> */}
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};
