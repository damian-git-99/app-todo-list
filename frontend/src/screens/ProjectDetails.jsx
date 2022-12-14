import React, { useContext, useEffect, useState } from 'react';
import { Alert, Button, Card, Col, Container, ListGroup, ProgressBar, Row, Spinner } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';
import { deleteProjectById, findProjectById } from '../api/ProjectApi';
import { deleteTaskById, toggleTask } from '../api/TaskApi';
import { UserContext } from '../context/ContextProvider';
import { confirmDialog, successMessage } from '../util/messages';
import { CreateTask } from './CreateTask';
import { TaskDetails } from './TaskDetails';

export const ProjectDetails = () => {
  const context = useContext(UserContext);
  const navigate = useNavigate();
  const params = useParams();
  const [isLoading, setisLoading] = useState(false);
  const [error, seterror] = useState(false);
  const [project, setproject] = useState({ tasks: [] });
  const [taskCreated, settaskCreated] = useState(false);
  const id = params.id;
  useEffect(() => {
    setisLoading(true);
    settaskCreated(false);
    findProjectById(context.token, id)
      .then((data) => setproject(data))
      .catch((e) => seterror(e.message))
      .finally((_) => setisLoading(false));
  }, [id, taskCreated]);

  const handleDeleteProject = () => {
    confirmDialog(() => {
      deleteProjectById(context.token, id)
        .then((_) => {
          successMessage('Project Deleted');
          navigate('/');
        })
        .catch((e) => console.log(e));
    });
  };

  const handleDeleteTask = (taskId) => {
    confirmDialog(() => {
      deleteTaskById(id, taskId, context.token)
        .then(response => {
          console.log(response);
          setproject({
            ...project,
            tasks: project.tasks.filter(task => task.id !== taskId)
          });
        })
        .catch(error => console.log(error.message));
    });
  };

  const handleToggleTask = (taskId) => {
    toggleTask(id, taskId, context.token)
      .then(_ => {
        setproject({
          ...project,
          tasks: project.tasks.map(task => {
            if (task.id === taskId) {
              task.complete = !task.complete;
            }
            return task;
          })
        });
      })
      .catch(error => console.log(error));
    console.log(taskId);
  };

  const calculatePercentageOfCompletedTasks = () => {
    const tasks = project.tasks;
    if (tasks.length === 0) return 0;
    const completedTasks = tasks.filter(task => task.complete === true);
    const total = (completedTasks.length / tasks.length) * 100;
    return Math.floor(total);
  };

  return (
    <Container className="mt-4 animate__animated animate__fadeIn">
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
              <CreateTask settaskCreated={settaskCreated} projectId={id} />
            </Col>
            <Col className="col-3">
              <ProgressBar
                striped
                variant="primary"
                now={calculatePercentageOfCompletedTasks().toString()}
                label={calculatePercentageOfCompletedTasks().toString() + '%'}
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
                    Created at: {project.createdAt}
                  </Card.Subtitle>
                  <Card.Text>{project.description}</Card.Text>
                </Card.Body>
                <ListGroup className="list-group list-group-flush">
                  { project.tasks.map(task => {
                    return (
                        <ListGroup.Item key={task.id} className="list-group-item">
                          <Row className='align-items-baseline'>
                            <Col>{ task.taskName }</Col>
                            <Col onClick={() => handleToggleTask(task.id)} >
                              { task.complete ? <i className="fa-solid fa-check text-success fs-4"></i> : <i className="fa-solid fa-xmark text-danger fs-4"></i> }
                            </Col>
                            <Col><TaskDetails task={task} /></Col>
                            <Col><Button onClick={() => handleDeleteTask(task.id)} variant='danger'>Delete task</Button></Col>
                          </Row>
                        </ListGroup.Item>
                    );
                  }) }
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
