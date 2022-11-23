import React, { useContext, useEffect, useState } from 'react';
import { Alert, Button, Card, Col, Container, ListGroup, ProgressBar, Row, Spinner } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';
import { deleteProjectById, findProjectById } from '../api/ProjectApi';
import { UserContext } from '../context/ContextProvider';
import { confirmDialog, successMessage } from '../util/messages';

export const ProjectDetails = () => {
  const context = useContext(UserContext);
  const navigate = useNavigate();
  const params = useParams();
  const [isLoading, setisLoading] = useState(false);
  const [error, seterror] = useState(false);
  const [project, setproject] = useState({});
  const id = params.id;

  useEffect(() => {
    setisLoading(true);
    findProjectById(context.token, id)
      .then((data) => setproject(data))
      .catch((e) => seterror(e.message))
      .finally((_) => setisLoading(false));
  }, [id]);

  const handleDeleteProject = (projectId) => {
    confirmDialog(() => {
      deleteProjectById(context.token, id)
        .then((_) => {
          successMessage('Project Deleted');
          navigate('/');
        })
        .catch((e) => console.log(e));
    });
  };

  return (
    <Container className="mt-4">
      {error && <Alert variant="danger">{error}</Alert>}
        <Row className=' justify-content-center'>
          <Col sm={1}>
            {isLoading && <Spinner animation='grow' /> }
          </Col>
        </Row>
      {!error && !isLoading
        ? (
        <Row>
          <Row className="row align-items-center justify-content-around">
            <Col className="col-3">
              <Button variant='outline-primary'>Add Task</Button>
            </Col>
            <Col className="col-3">
              <ProgressBar
                striped
                variant="primary"
                now={25}
                label='25%'
              />
            </Col>
            <Col className="col-3">
              <Button
                onClick={() => handleDeleteProject(project.id)}
                variant='outline-danger'
              >
                Delete Project
              </Button>
            </Col>
          </Row>
          <Row className="mt-5">
            <Col>
              <Card>
                <Card.Body className="card-body">
                  <Card.Title>{project.name}</Card.Title>
                  <Card.Subtitle>
                    Created at: {new Date(project.createdAt).toString()}
                  </Card.Subtitle>
                  <Card.Text>{project.description}</Card.Text>
                </Card.Body>
                <ListGroup className="list-group list-group-flush">
                  <ListGroup.Item className="list-group-item">
                    <Row>
                      <Col>Task #1</Col>
                      <Col>mark as completed</Col>
                      <Col>Delete task</Col>
                    </Row>
                  </ListGroup.Item>
                  <ListGroup.Item>Task #1</ListGroup.Item>
                  <ListGroup.Item>Task #1</ListGroup.Item>
                </ListGroup>
              </Card>
            </Col>
          </Row>
        </Row>
          )
        : null }
    </Container>
  );
};
