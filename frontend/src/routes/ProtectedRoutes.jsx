import React, { useContext } from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { NavBar } from '../components/NavBar';
import { UserContext } from '../context/ContextProvider';

export const ProtectedRoutes = () => {
  const userContext = useContext(UserContext);
  const { isAuthenticated } = userContext;
  if (!isAuthenticated) {
    return <Navigate to={'/login'} replace />;
  }
  return (
    <>
      <NavBar />
      <Outlet />
    </>
  );
};
