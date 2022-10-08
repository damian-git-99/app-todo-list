import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { ErrorPage } from '../screens/ErrorPage';
import { SignUp } from '../screens/SignUp';

export const AppRoutes = () => {
  return (
    <Routes>
      <Route path="*" element={<ErrorPage />} />
      <Route path="/signup" element={<SignUp />} />
      {/* <Route path="/:shortURL" element={<GetOriginalURL />} /> */}
    </Routes>
  );
};
