/* eslint-disable react/prop-types */
import React, { useEffect, useState } from 'react';
import { userInfo } from '../api/UserApi';

const user = {
  isAuthenticated: false,
  email: '',
  username: '',
  token: '',
  setUser: () => {}
};

const loadUserInfo = () => {
  try {
    if (localStorage.getItem('token')) {
      // get token from localstorage
      const userJSON = localStorage.getItem('token');
      const token = JSON.parse(userJSON);
      return {
        isAuthenticated: true,
        email: '',
        username: '',
        token
      };
    }
    return user;
  } catch (error) {
    localStorage.removeItem('token');
    return user;
  }
};

export const UserContext = React.createContext(user);

export const ContextProvider = (props) => {
  const [userState, setuserState] = useState(loadUserInfo());
  useEffect(() => {
    if (userState.isAuthenticated) {
      // update user info
      userInfo(userState.token)
        .then(data => {
          setuserState(state => {
            return {
              ...state,
              ...data
            };
          });
        })
        .catch(error => {
          console.log(error);
        });
    }
  }, []);
  return (
    <UserContext.Provider value={ { ...userState, setUser: setuserState } }>
        { props.children }
    </UserContext.Provider>
  );
};
