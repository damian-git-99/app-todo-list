import axios from 'axios';

const URL = 'http://127.0.0.1:8080/api/v1/tasks';

export const createTask = async (projectId, task, token) => {
  console.log(token);
  const config = {
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    }
  };
  try {
    const { data } = await axios.post(`${URL}/${projectId}`, task, config);
    return data;
  } catch (error) {
    const message = error?.response?.data?.error || error.message;
    const err = new Error(message);
    throw err;
  }
};

export const deleteTaskById = async (projectId, taskId, token) => {
  const config = {
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    }
  };
  try {
    const { data } = await axios.delete(`${URL}/${projectId}/${taskId}`, config);
    return data;
  } catch (error) {
    const message = error?.response?.data?.error || error.message;
    const err = new Error(message);
    throw err;
  }
};

export const toggleTask = async (projectId, taskId, token) => {
  try {
    // todo: resolve using axios here return an 403 error, but with fetch everything works perfect
    const { data } = await fetch(`${URL}/${projectId}/${taskId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      }
    });
    return data;
  } catch (error) {
    console.log(error);
    const message = error?.response?.data?.error || error.message;
    const err = new Error(message);
    throw err;
  }
};
