import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import { Card, Col } from 'react-bootstrap';

export const ProjectCard = ({ project }) => {
  const { id, name, createdAt, description } = project;
  return (
    <Col md={4} className='g-2 g-md-2'>
      <Card className="card">
        <Card.Body>
          <Card.Title>{name}</Card.Title>
          <Card.Subtitle>{createdAt}</Card.Subtitle>
          <Card.Text>{description} </Card.Text>
          <Link to={`/projects/${id}`} className="btn btn-dark">
            View Project
          </Link>
        </Card.Body>
      </Card>
    </Col>
  );
};

ProjectCard.propTypes = {
  project: PropTypes.object.isRequired
};
