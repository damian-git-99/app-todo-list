import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { signup } from '../api/UserApi';
import { Alert } from '../components/Alert';
import { Spinner } from '../components/Spinner';
import { successMessage } from '../util/messages';
import { isThereAnEmptyField } from '../util/validations';

export const SignUp = () => {
  const initialState = {
    username: '',
    email: '',
    password: '',
    repeatPassword: ''
  };
  const navigate = useNavigate();
  const [form, setform] = useState(initialState);
  const [loading, setloading] = useState(false);
  const [error, seterror] = useState(false);

  const { username, email, password, repeatPassword } = form;

  const handleOnChange = (e) => {
    const value = e.target.value;
    setform({ ...form, [e.target.name]: value });
  };

  const handleSumbit = (e) => {
    e.preventDefault();

    if (isThereAnEmptyField(username, email, password, repeatPassword)) {
      seterror('All fields must be completed');
      return;
    }

    if (password !== repeatPassword) {
      seterror("Passwords don't match");
      return;
    }

    setloading(true);
    seterror(false);
    signup({ ...form })
      .then(data => {
        successMessage('User Registration complete!');
        setform(initialState);
        navigate('/signin');
      })
      .catch(e => {
        seterror(e.message);
      })
      .finally(() => setloading(false));
  };

  return (
    <div className="container-lg">
      <div style={{ height: '500px' }} className="row justify-content-center align-items-center">
        <h1 className="text-center">Signup</h1>
        { error && <Alert message={error} type='danger' /> }
        { loading && <Spinner /> }
        <div className="col-12 col-md-6">
          <form action="" onSubmit={handleSumbit}>
            <div className="row mb-4">
              <div className="col-lg-12">
                <input
                  type="text"
                  placeholder="Username"
                  className="form-control form-control-lg fs-6 border-0 shadow-sm"
                  value={username}
                  onChange={handleOnChange}
                  name="username"
                />
              </div>
            </div>

            <div className="row mb-4">
              <div className="col-lg-12">
                <input
                  type="email"
                  placeholder="Email"
                  className="form-control form-control-lg fs-6 border-0 shadow-sm"
                  value={email}
                  onChange={handleOnChange}
                  name="email"
                />
              </div>
            </div>

            <div className="row mb-4">
              <div className="col-lg-12">
                <input
                  type="password"
                  placeholder="Password"
                  className="form-control form-control-lg fs-6 border-0 shadow-sm"
                  value={password}
                  onChange={handleOnChange}
                  name="password"
                />
              </div>
            </div>

            <div className="row mb-4">
              <div className="col-lg-12">
                <input
                  type="password"
                  placeholder="repeat password"
                  className="form-control form-control-lg fs-6 border-0 shadow-sm"
                  value={repeatPassword}
                  onChange={handleOnChange}
                  name="repeatPassword"
                />
              </div>
            </div>

            <div className="row">
              <div className="col">
                <button type="submit" className="btn btn-primary px-3">
                  Sign up
                </button>
              </div>
              <div className="col">
                <a href="">Ya tengo cuenta</a>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};
