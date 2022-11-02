import React, { useContext, useEffect, useState } from 'react';
import { findAllprojects } from '../api/ProjectApi';
import { Alert } from '../components/Alert';
import { Card } from '../components/Card';
import { Spinner } from '../components/Spinner';
import { UserContext } from '../context/ContextProvider';

export const Home = () => {
  const context = useContext(UserContext);
  const [projects, setprojects] = useState([]);
  const [isLoading, setisLoading] = useState(true);
  const [error, seterror] = useState(false);

  useEffect(() => {
    findAllprojects(context.token)
      .then((data) => {
        setprojects(data);
      })
      .catch((e) => seterror(e))
      .finally(() => setisLoading(false));
  }, []);

  return (
    <div className="container mt-5">
      <h1 className='text-center'>My Projects</h1>
      { isLoading && <Spinner />}
      {error && <Alert message={error} type="danger" />}
      <div className="row mt-4">
          {projects.map((p) => (
            <Card key={p.id} project={p} />
          ))}
      </div>
    </div>
  );
};
