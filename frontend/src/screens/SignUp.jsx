import React, { useState } from 'react';
import { signup } from '../api/UserApi';
import { Spinner } from '../components/Spinner';
import { errorMessage, successMessage } from '../util/messages';
import { isThereAnEmptyField } from '../util/validations';

export const SignUp = () => {
  const initialState = {
    username: '',
    email: '',
    password: '',
    repeatPassword: ''
  };

  const [form, setform] = useState(initialState);
  const [loading, setloading] = useState(false);

  const { username, email, password, repeatPassword } = form;

  const handleOnChange = (e) => {
    const value = e.target.value;
    setform({ ...form, [e.target.name]: value });
  };

  const handleSumbit = (e) => {
    e.preventDefault();

    if (isThereAnEmptyField(username, email, password, repeatPassword)) {
      errorMessage('All fields must be completed');
      return;
    }

    if (password !== repeatPassword) {
      errorMessage("Passwords don't match");
      return;
    }

    setloading(true);
    signup({ ...form })
      .then(data => {
        console.log(data);
        successMessage('User Registration complete!');
        // todo send user to login page
      })
      .catch(e => {
        errorMessage(e);
      })
      .finally(() => setloading(false));
  };

  return (
    <div className="container-lg">
      <div className="row vh-100 justify-content-center align-items-center">
        <h1 className="text-center">Signup</h1>
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
                <button type="submit" className="btn btn-primary px-3 w-50">
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
