import React, { useState } from 'react';
import PropTypes from 'prop-types';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

export function TaskDetails ({ task }) {
  const [show, setShow] = useState(false);
  return (
    <>
      <Button variant="info" onClick={() => setShow(true)}>
        Details
      </Button>

      <Modal
        show={show}
        onHide={() => setShow(false)}
        dialogClassName="modal-90w"
        aria-labelledby="example-custom-modal-styling-title"
      >
        <Modal.Header closeButton>
          <Modal.Title id="example-custom-modal-styling-title">
             { task.taskName }
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <p>
            { task.description }
          </p>
          <p>Priority: {task.priority} </p>
        </Modal.Body>
      </Modal>
    </>
  );
}

TaskDetails.propTypes = {
  task: PropTypes.object.isRequired
};
