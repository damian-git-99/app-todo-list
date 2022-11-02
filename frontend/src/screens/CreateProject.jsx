import React, { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createProject } from '../api/ProjectApi';
import { Alert } from '../components/Alert';
import { Spinner } from '../components/Spinner';
import { UserContext } from '../context/ContextProvider';
import { successMessage } from '../util/messages';
import { isThereAnEmptyField } from '../util/validations';

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
  <div className='container mt-4'>
    <div className="row">
      <div className="col">
        <button className='btn btn-outline-primary px-4'>Return</button>
      </div>
      <div className="col">
        {error && <Alert message={error} type="danger" />}
        {isLoading && <Spinner />}
      </div>
    </div>
    <div className="row justify-content-center align-items-center pt-5">
      <div className="col-md-5">
      <form onSubmit={handleSubmit}>
            <div className="row mb-4">
              <div className="col-lg-12">
                <input
                  type="text"
                  placeholder="Name"
                  className="form-control form-control-lg fs-6 border-0 shadow-sm"
                  value={name}
                  onChange={handleOnChange}
                  name="name"
                />
              </div>
            </div>

            <div className="row mb-4">
              <div className="col-lg-12">
                <textarea
                  rows={10}
                  type="text"
                  placeholder="Description"
                  className="form-control form-control-lg fs-6 border-0 shadow-sm"
                  value={description}
                  onChange={handleOnChange}
                  name="description"></textarea>
              </div>
            </div>

            <div className="row">
              <div className="col">
                <button type="submit" className="btn btn-primary px-3">
                  Create Project
                </button>
              </div>
            </div>
          </form>
      </div>
    </div>
  </div>);
};
