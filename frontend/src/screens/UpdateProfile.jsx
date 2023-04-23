import React, { useState, useEffect, useContext } from 'react';
import { Button, Col, Container, Form, Row } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';
import { updateUser } from '../api/UserApi';
import { UserContext } from '../context/ContextProvider';
import { successMessage } from '../util/messages';

export const UpdateProfile = () => {
  const context = useContext(UserContext);
  const navigate = useNavigate();
  const initialForm = {
    username: '',
    email: ''
  };

  const params = useParams();
  const [form, setform] = useState(initialForm);
  const userId = params.id;
  const { username, email } = form;

  useEffect(() => {
    setform({
      username: context.username,
      email: context.email
    });
  }, [context]);

  const handleOnChange = (e) => {
    const value = e.target.value;
    setform({ ...form, [e.target.name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    updateUser(userId, form, context.token)
      .then((res) => {
        context.setUser((state) => {
          return { ...state, ...res };
        });
        successMessage('User Updated');
        navigate('/');
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <Container fluid="lg" className='animate__animated animate__fadeIn'>
      <Row
        style={{ height: '500px' }}
        className="justify-content-center align-items-center"
      >
        <h1 className="text-center">Update User</h1>
        <Col md={6}>
          <Form onSubmit={handleSubmit}>

          <Row className="mb-4">
              <Col lg={12}>
                <Form.Control
                    type='text'
                    name='username'
                    value={username}
                    onChange={handleOnChange}
                    className="fs-6 border-0 shadow-sm"
                />
              </Col>
            </Row>

            <Row className="mb-4">
              <Col lg={12}>
                <Form.Control
                  type='email'
                  name='email'
                  value={email}
                  onChange={handleOnChange}
                  className="fs-6 border-0 shadow-sm"
                />
              </Col>
            </Row>

            <Row>
              <Col>
                <Button type="submit" variant='primary' className="px-3"> Update </Button>
              </Col>
            </Row>

          </Form>
        </Col>
      </Row>
    </Container>
  );
};
