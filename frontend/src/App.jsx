import React, { useState } from 'react';
import { AppRoutes } from './routes/AppRoutes';

const user = {
  isAuthenticated: false,
  email: '',
  name: '',
  token: '',
  setUser: () => {}
};

export const UserContext = React.createContext(user);

const loadValues = () => {
  try {
    if (localStorage.getItem('user')) {
      const userJSON = localStorage.getItem('user');
      const user = JSON.parse(userJSON);
      return {
        isAuthenticated: true,
        email: user.email || '',
        name: user.name || '',
        token: user.token || ''
      };
    }
    return user;
  } catch (error) {
    return user;
  }
};

function App () {
  const [userState, setuserState] = useState(loadValues());

  return (
    <UserContext.Provider value={ { ...userState, setUser: setuserState } }>
      <AppRoutes />
    </UserContext.Provider>
  );
}

export default App;
