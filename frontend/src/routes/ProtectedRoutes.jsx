import React, { useContext } from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { UserContext } from '../App';

export const ProtectedRoutes = () => {
  const userContext = useContext(UserContext);
  const { isAuthenticated } = userContext;
  if (!isAuthenticated) {
    return <Navigate to={'/login'} replace />;
  }
  return <Outlet />;
};
