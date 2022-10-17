import { Link, useNavigate } from 'react-router-dom';
import React, { useContext, useState } from 'react';
import { Spinner } from '../components/Spinner';
import { Alert } from '../components/Alert';
import { isThereAnEmptyField } from '../util/validations';
import { login } from '../api/UserApi';
import { UserContext } from '../context/ContextProvider';

export const LogIn = () => {
  const userContext = useContext(UserContext);
  const navigate = useNavigate();
  if (userContext.isAuthenticated) {
    navigate('/');
    return;
  }

  const initialState = {
    email: '',
    password: ''
  };
  const [loading, setloading] = useState(false);
  const [error, seterror] = useState(false);
  const [form, setform] = useState(initialState);

  const { email, password } = form;

  const handleOnChange = (e) => {
    const value = e.target.value;
    setform({ ...form, [e.target.name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (isThereAnEmptyField(email, password)) {
      seterror('All fields must be completed');
      return;
    };

    setloading(true);
    seterror(false);
    login({ ...form })
      .then(data => {
        userContext.setUser((state) => {
          return { ...state, ...data, isAuthenticated: true };
        });
        navigate('/');
      })
      .catch(e => seterror(e.message))
      .finally(() => setloading(false));
  };

  return (
    <div className="container-lg">
      <div
        style={{ height: '500px' }}
        className="row justify-content-center align-items-center"
      >
        <h1 className="text-center">Log in</h1>
        {error && <Alert message={error} type="danger" />}
        {loading && <Spinner />}
        <div className="col-12 col-md-6">
          <form action="" onSubmit={handleSubmit}>
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

            <div className="row">
              <div className="col">
                <button type="submit" className="btn btn-primary px-3">
                  Sign up
                </button>
              </div>
              <div className="col">
                <Link to="/signup">I do not have an account</Link>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};
