import React from 'react';
import PropTypes from 'prop-types';

export const Alert = ({ message, type = 'success' }) => {
  return (
    <div className='row animate__animated animate__bounceIn'>
      <div className={`alert alert-${type}`}>
      { message }
    </div>
    </div>
  );
};

Alert.propTypes = {
  message: PropTypes.string.isRequired,
  type: PropTypes.string.isRequired
};
