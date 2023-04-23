import React, { useContext, useState } from 'react';
import { Alert, Button, Col, Container, Form, Row, Spinner } from 'react-bootstrap';
import { useNavigate, Link } from 'react-router-dom';
import { signup } from '../api/UserApi';
import { UserContext } from '../context/ContextProvider';
import { successMessage } from '../util/messages';
import { isThereAnEmptyField } from '../util/validations';

export const SignUp = () => {
  const userContext = useContext(UserContext);
  const navigate = useNavigate();
  if (userContext.isAuthenticated) {
    navigate('/');
    return;
  }
  const initialState = {
    username: '',
    email: '',
    password: '',
    repeatPassword: ''
  };

  const [form, setform] = useState(initialState);
  const [loading, setloading] = useState(false);
  const [error, seterror] = useState(false);

  const { username, email, password, repeatPassword } = form;

  const handleOnChange = (e) => {
    const value = e.target.value;
    setform({ ...form, [e.target.name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (isThereAnEmptyField(username, email, password, repeatPassword)) {
      seterror('All fields must be completed');
      return;
    }

    if (password !== repeatPassword) {
      seterror("Passwords don't match");
      return;
    }

    setloading(true);
    seterror(false);
    signup({ ...form })
      .then(data => {
        successMessage('User Registration complete!');
        setform(initialState);
        navigate('/login');
      })
      .catch(e => {
        seterror(e.message);
      })
      .finally(() => setloading(false));
  };

  return (
    <Container fluid='lg' className='animate__animated animate__fadeIn'>
      <Row style={{ height: '500px' }} className="justify-content-center align-items-center">
        <h1 className="text-center">Signup</h1>
        {error && <Alert variant="danger">{error}</Alert>}
        <Row className=' justify-content-center'>
          <Col sm={1}>
            {loading && <Spinner animation='grow' /> }
          </Col>
        </Row>
        <Col md={6}>
          <Form action="" onSubmit={handleSubmit}>
            <Row className="mb-4">
              <Col lg={12} >
                <Form.Control
                    type='text'
                    placeholder='Username'
                    name='username'
                    value={username}
                    onChange={handleOnChange}
                    className="form-control form-control-lg fs-6 border-0 shadow-sm"
                  />
              </Col>
            </Row>

            <Row className="mb-4">
              <Col lg={12}>
                <Form.Control
                  type='email'
                  placeholder='Email'
                  name='email'
                  value={email}
                  onChange={handleOnChange}
                  className="form-control form-control-lg fs-6 border-0 shadow-sm"
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
                  className="form-control form-control-lg fs-6 border-0 shadow-sm"
                />
              </Col>
            </Row>

            <Row className="mb-4">
              <Col lg={12}>
                <Form.Control
                  type='password'
                  placeholder='Repeat password'
                  name='repeatPassword'
                  value={repeatPassword}
                  onChange={handleOnChange}
                  className="form-control form-control-lg fs-6 border-0 shadow-sm"
                />
              </Col>
            </Row>

            <Row>
              <Col>
                <Button type="submit" variant='primary' className="px-3">
                  Sign up
                </Button>
              </Col>
              <Col>
                <Link to="/login">I already have an account</Link>
              </Col>
            </Row>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};
