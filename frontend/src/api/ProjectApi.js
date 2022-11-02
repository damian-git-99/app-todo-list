import axios from 'axios';

export const createProject = async (project, token) => {
  const config = {
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    }
  };
  try {
    const { data } = await axios.post('http://127.0.0.1:8080/api/v1/projects', project, config);
    return data;
  } catch (error) {
    const message = error?.response?.data?.error || error.message;
    const err = new Error(message);
    throw err;
  }
};

export const findAllprojects = async (token) => {
  const config = {
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    }
  };
  try {
    const { data } = await axios.get('http://127.0.0.1:8080/api/v1/projects', config);
    return data;
  } catch (error) {
    const message = error?.response?.data?.error || error.message;
    const err = new Error(message);
    throw err;
  }
};
