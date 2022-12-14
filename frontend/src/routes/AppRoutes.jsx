import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { CreateProject } from '../screens/CreateProject';
import { ErrorPage } from '../screens/ErrorPage';
import { Home } from '../screens/Home';
import { LogIn } from '../screens/LogIn';
import { ProjectDetails } from '../screens/ProjectDetails';
import { SignUp } from '../screens/SignUp';
import { UpdateProfile } from '../screens/UpdateProfile';
import { ProtectedRoutes } from './ProtectedRoutes';

export const AppRoutes = () => {
  return (
    <>
    <Routes>
      <Route path="*" element={<ErrorPage />} />
      <Route path="/signup" element={<SignUp />} />
      <Route path="/login" element={<LogIn />} />
      <Route element={<ProtectedRoutes />}>
        <Route path='/' element={<Home/>} />
        <Route path='/projects/create-project' element={<CreateProject />} />
        <Route path='/projects/:id' element={<ProjectDetails />} />
        <Route path='/edit-profile/:id' element={<UpdateProfile />} />
      </Route>
    </Routes>
    </>
  );
};
