import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { findProjectById } from '../api/ProjectApi';
import { UserContext } from '../context/ContextProvider';

export const ProjectDetails = () => {
  const context = useContext(UserContext);
  const params = useParams();
  const [project, setproject] = useState({});
  const id = params.id;
  useEffect(() => {
    findProjectById(context.token, id)
      .then(data => setproject(data));
  }, [id]);

  return (
    <div className='container mt-4'>
      <div className="row align-items-center justify-content-around">
        <div className="col-3">
          <button className='btn btn-outline-primary'>Add Task</button>
        </div>
        <div className="col-3">
          <div className="progress">
            <div className="progress-bar progress-bar-striped" role="progressbar" style={{ width: '25%' }} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
          </div>
        </div>
        <div className="col-3">
          <button className='btn btn-outline-danger'>Delete Project</button>
        </div>
      </div>
      <div className="row mt-5">
        <div className="col-12">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">{project.name}</h5>
              <h6 className="card-subtitle mb-2 text-muted">Created at: {new Date(project.createdAt).toString()}</h6>
              <p className="card-text">{project.description}</p>
            </div>
            <ul className="list-group list-group-flush">
              <li className="list-group-item">
                <div className="row">
                  <div className="col">Task #1</div>
                  <div className="col">mark as completed</div>
                  <div className="col">Delete task</div>
                </div>
              </li>
              <li className="list-group-item">Task #1</li>
              <li className="list-group-item">Task #1</li>
          </ul>
          </div>
        </div>
      </div>
    </div>
  );
};
