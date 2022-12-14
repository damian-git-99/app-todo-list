import { Link, useNavigate } from 'react-router-dom';
import React, { useContext, useState } from 'react';
import { isThereAnEmptyField } from '../util/validations';
import { login } from '../api/UserApi';
import { UserContext } from '../context/ContextProvider';
import { Alert, Button, Col, Container, Form, Row, Spinner } from 'react-bootstrap';

export const LogIn = () => {
  const userContext = useContext(UserContext);
  const navigate = useNavigate();
  if (userContext.isAuthenticated) {
    navigate('/');
    return;
  }

  const initialState = {
    email: '',
    password: ''
  };
  const [loading, setloading] = useState(false);
  const [error, seterror] = useState(false);
  const [form, setform] = useState(initialState);

  const { email, password } = form;

  const handleOnChange = (e) => {
    const value = e.target.value;
    setform({ ...form, [e.target.name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (isThereAnEmptyField(email, password)) {
      seterror('All fields must be completed');
      return;
    };

    setloading(true);
    seterror(false);
    login({ ...form })
      .then(data => {
        userContext.setUser((state) => {
          return { ...state, ...data, isAuthenticated: true };
        });
        navigate('/');
      })
      .catch(e => seterror(e.message))
      .finally(() => setloading(false));
  };

  return (
    <Container fluid="lg" className='animate__animated animate__fadeIn'>
      <Row
        style={{ height: '500px' }}
        className="justify-content-center align-items-center"
      >
        <h1 className="text-center">Log in</h1>
        {error && <Alert variant="danger">{error}</Alert>}
        <Row className=' justify-content-center'>
          <Col sm={1}>
            {loading && <Spinner animation='grow' /> }
          </Col>
        </Row>
        <Col md={6}>
          <Form onSubmit={handleSubmit}>

            <Row className="mb-4">
              <Col lg={12}>
                <Form.Control
                  type='email'
                  placeholder='Email'
                  name='email'
                  value={email}
                  onChange={handleOnChange}
                  className="fs-6 border-0 shadow-sm"
                />
              </Col>
            </Row>

            <Row className="mb-4">
              <Col lg={12}>
                <Form.Control
                    type='password'
                    placeholder='Password'
                    name='password'
                    value={password}
                    onChange={handleOnChange}
                    className="fs-6 border-0 shadow-sm"
                />
              </Col>
            </Row>

            <Row>
              <Col>
                <Button type="submit" variant='primary' className="px-3"> Sign up </Button>
              </Col>
              <Col>
                <Link to="/signup">I do not have an account</Link>
              </Col>
            </Row>

          </Form>
        </Col>
      </Row>
    </Container>
  );
};
