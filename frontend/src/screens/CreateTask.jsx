import React, { useContext, useState } from 'react';
import PropTypes from 'prop-types';
import { FormGroup } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import { isThereAnEmptyField } from '../util/validations';
import { createTask } from '../api/TaskApi';
import { UserContext } from '../context/ContextProvider';

export const CreateTask = ({ projectId, settaskCreated }) => {
  const userContext = useContext(UserContext);
  const initialState = {
    taskName: '',
    description: '',
    priority: 'Priority'
  };
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const [form, setform] = useState(initialState);
  const { taskName, description, priority } = form;

  const handleOnChange = (e) => {
    const value = e.target.value;
    setform({ ...form, [e.target.name]: value });
  };

  const submitHandle = (e) => {
    if (priority === 'Priority' || isThereAnEmptyField(taskName, description)) {
      return;
    }
    createTask(projectId, { ...form }, userContext.token)
      .then(res => {
        console.log(res);
        setform(initialState);
        setShow(false);
        settaskCreated(true);
      })
      .catch(e => console.log(e.message));
  };

  return (
    <>
      <Button variant="outline-primary" onClick={handleShow}>
        Add Task
      </Button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Create a Task</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Something..."
                name="taskName"
                value={taskName}
                onChange={handleOnChange}
                autoFocus
              />
            </Form.Group>
            <Form.Group
              className="mb-3"
              controlId="exampleForm.ControlTextarea1"
            >
              <Form.Label>Description</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                name="description"
                value={description}
                onChange={handleOnChange}
              />
            </Form.Group>

            <FormGroup>
              <Form.Select
                aria-label="Default select example"
                onChange={handleOnChange}
                name="priority"
                value={priority}
              >
                <option>Priority</option>
                <option value="LOW">LOW</option>
                <option value="MEDIUM">MEDIUM</option>
                <option value="HIGH">HIGH</option>
              </Form.Select>
            </FormGroup>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={submitHandle}>
            Save Changes
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

CreateTask.propTypes = {
  projectId: PropTypes.string.isRequired,
  settaskCreated: PropTypes.func.isRequired
};
