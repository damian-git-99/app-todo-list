/* eslint-disable react/prop-types */
import React, { useState, useEffect } from 'react';
import { userInfo } from '../api/UserApi';

const user = {
  isAuthenticated: false,
  email: '',
  username: '',
  token: '',
  setUser: () => {}
};

const loadTokenFromLocalStorage = () => {
  try {
    if (localStorage.getItem('token')) {
      const userJSON = localStorage.getItem('token');
      const token = JSON.parse(userJSON);
      return token;
    }
    return null;
  } catch (error) {
    return null;
  }
};

export const UserContext = React.createContext(user);

export const ContextProvider = (props) => {
  const [userState, setUserState] = useState(user);

  const initialState = async () => {
    const token = loadTokenFromLocalStorage();
    if (!token) return;
    const response = await userInfo(token);
    const { email, username } = response;
    setUserState({
      isAuthenticated: true,
      email,
      username,
      token
    });
  };

  useEffect(() => {
    initialState();
  }, []);

  return (
    <UserContext.Provider value={ { ...userState, setUser: setUserState } }>
        { props.children }
    </UserContext.Provider>
  );
};
