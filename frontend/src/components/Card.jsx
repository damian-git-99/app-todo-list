import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

export const Card = ({ project }) => {
  const { id, name, createdAt, description } = project;
  return (
    <div className='col-12 col-md-4 g-2 g-md-2'>
      <div className="card">
        <div className="card-body">
          <h5 className="card-title">{name}</h5>
          <h6 className="card-subtitle mb-2 text-muted">{createdAt}</h6>
          <p className="card-text">{description} </p>
          <Link to={`/projects/${id}`} className="btn btn-dark">
            View Project
          </Link>
        </div>
      </div>
    </div>
  );
};

Card.propTypes = {
  project: PropTypes.object.isRequired
};
