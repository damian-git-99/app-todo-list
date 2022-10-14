import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { ErrorPage } from '../screens/ErrorPage';
import { Home } from '../screens/Home';
import { LogIn } from '../screens/LogIn';
import { SignUp } from '../screens/SignUp';
import { ProtectedRoutes } from './ProtectedRoutes';

export const AppRoutes = () => {
  return (
    <Routes>
      <Route path="*" element={<ErrorPage />} />
      <Route path="/signup" element={<SignUp />} />
      <Route path="/login" element={<LogIn />} />
      <Route element={<ProtectedRoutes />}>
        <Route path='/' element={<Home/>} />
      </Route>
    </Routes>
  );
};
