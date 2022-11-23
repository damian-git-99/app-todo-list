/* eslint-disable react/no-unescaped-entities */
import React, { useContext, useEffect, useState } from 'react';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Alert from 'react-bootstrap/Alert';
import { findAllprojects } from '../api/ProjectApi';
import { ProjectCard } from '../components/ProjectCard';
import { UserContext } from '../context/ContextProvider';
import { Col, Spinner } from 'react-bootstrap';

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
      .catch((e) => seterror(e.message))
      .finally(() => setisLoading(false));
  }, []);

  return (
    <Container className="container mt-5 pb-2">
      <h1 className='text-center'>My Projects</h1>
      {error && <Alert variant="danger">{error}</Alert>}
        <Row className=' justify-content-center'>
          <Col sm={1}>
            {isLoading && <Spinner animation='grow' /> }
          </Col>
        </Row>
      { !error && projects && projects.length === 0 ? <Alert variant='dark'>you don't have any project ...</Alert> : null }
      <Row className="mt-4">
          {projects.map((p) => (
            <ProjectCard key={p.id} project={p} />
          ))}
      </Row>
    </Container>
  );
};
