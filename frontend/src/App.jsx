import React from 'react';
import { ContextProvider } from './context/ContextProvider';
import { AppRoutes } from './routes/AppRoutes';

function App () {
  return (
    <ContextProvider>
      <AppRoutes />
    </ContextProvider>
  );
}

export default App;
