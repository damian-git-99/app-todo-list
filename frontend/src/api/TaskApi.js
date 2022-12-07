import axios from 'axios';

export const createTask = async (proyectId, task, token) => {
  console.log(token);
  const config = {
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    }
  };
  try {
    const { data } = await axios.post(`http://127.0.0.1:8080/api/v1/tasks/${proyectId}`, task, config);
    return data;
  } catch (error) {
    const message = error?.response?.data?.error || error.message;
    const err = new Error(message);
    throw err;
  }
};

export const deleteTaskById = async (proyectId, taskId, token) => {
  const config = {
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    }
  };
  try {
    const { data } = await axios.delete(`http://127.0.0.1:8080/api/v1/tasks/${proyectId}/${taskId}`, config);
    return data;
  } catch (error) {
    const message = error?.response?.data?.error || error.message;
    const err = new Error(message);
    throw err;
  }
};

export const toggleTask = async (proyectId, taskId, token) => {
  try {
    // todo: resolve using axios here return an 403 error, but with fetch everything works perfect
    const { data } = await fetch(`http://127.0.0.1:8080/api/v1/tasks/${proyectId}/${taskId}`, {
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
