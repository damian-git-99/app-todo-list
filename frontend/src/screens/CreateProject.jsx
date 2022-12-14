import React, { useContext, useState } from 'react';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Alert from 'react-bootstrap/Alert';
import { useNavigate, Link } from 'react-router-dom';
import { createProject } from '../api/ProjectApi';
import { UserContext } from '../context/ContextProvider';
import { successMessage } from '../util/messages';
import { isThereAnEmptyField } from '../util/validations';
import { Button, Col, Form, Spinner } from 'react-bootstrap';

export const CreateProject = () => {
  const navigate = useNavigate();
  const context = useContext(UserContext);
  const initialState = {
    name: '',
    description: ''
  };
  const [isLoading, setisLoading] = useState(false);
  const [error, seterror] = useState(false);
  const [form, setform] = useState(initialState);
  const { name, description } = form;

  const handleOnChange = (e) => {
    const value = e.target.value;
    setform({ ...form, [e.target.name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    seterror(false);

    if (isThereAnEmptyField(name, description)) {
      seterror('All fields must be completed');
      return;
    }

    setisLoading(true);
    createProject({ ...form }, context.token)
      .then((data) => {
        successMessage('Project created!');
        navigate('/');
      })
      .catch((e) => {
        console.log(e);
        seterror(e.message);
      })
      .finally(() => setisLoading(false));
  };

  return (
    <Container className="mt-4">
      <Row>
        <Col>
          <Link to={'/'} className="btn btn-outline-dark px-4">Return</Link>
        </Col>
        <Col>
          {error && <Alert variant="danger">{error}</Alert>}
          <Row className=' justify-content-center'>
            <Col sm={1}>
              {isLoading && <Spinner animation='grow' /> }
            </Col>
          </Row>
        </Col>
      </Row>

      <Row className="justify-content-center align-items-center pt-5">
        <Col md={5}>
          <Form onSubmit={handleSubmit}>
            <Row className="mb-4">
              <Col lg={12}>
                <Form.Control
                  type='text'
                  placeholder='Name'
                  className='fs-6 border-0 shadow-sm'
                  value={name}
                  onChange={handleOnChange}
                  name='name'
                />
              </Col>
            </Row>

            <Row className="mb-4">
              <Col lg={12}>
                <textarea
                  rows={10}
                  type="text"
                  placeholder="Description"
                  className="form-control form-control-lg fs-6 border-0 shadow-sm"
                  value={description}
                  onChange={handleOnChange}
                  name="description"
                ></textarea>
              </Col>
            </Row>

            <Row>
              <Col>
                <Button type="submit" variant="dark" className="px-3">
                  Create Project
                </Button>
              </Col>
            </Row>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};
