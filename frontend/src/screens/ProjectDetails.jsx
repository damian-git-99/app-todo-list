import React, { useContext, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { deleteProjectById, findProjectById } from '../api/ProjectApi';
import { Alert } from '../components/Alert';
import { Spinner } from '../components/Spinner';
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
    <div className="container mt-4">
      {error && <Alert message={error} type="danger" />}
      {isLoading && <Spinner />}
      {!error && !isLoading
        ? (
        <row>
          <div className="row align-items-center justify-content-around">
            <div className="col-3">
              <button className="btn btn-outline-primary">Add Task</button>
            </div>
            <div className="col-3">
              <div className="progress">
                <div
                  className="progress-bar progress-bar-striped"
                  role="progressbar"
                  style={{ width: '25%' }}
                  aria-valuenow="25"
                  aria-valuemin="0"
                  aria-valuemax="100"> 25%
                </div>
              </div>
            </div>
            <div className="col-3">
              <button
                onClick={() => handleDeleteProject(project.id)}
                className="btn btn-outline-danger"
              >
                Delete Project
              </button>
            </div>
          </div>
          <div className="row mt-5">
            <div className="col-12">
              <div className="card">
                <div className="card-body">
                  <h5 className="card-title">{project.name}</h5>
                  <h6 className="card-subtitle mb-2 text-muted">
                    Created at: {new Date(project.createdAt).toString()}
                  </h6>
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
        </row>
          )
        : null }
    </div>
  );
};
