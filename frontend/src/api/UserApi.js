import axios from 'axios';

const URL = 'http://127.0.0.1:8080/api/v1';

export const signup = async (user) => {
  try {
    const { data } = await axios.post(`${URL}/users`, user);
    return data;
  } catch (error) {
    const message = error?.response?.data?.error || error.message;
    const err = new Error(message);
    throw err;
  }
};

export const login = async (user) => {
  try {
    const { data } = await axios.post(`${URL}/auth`, user);
    const { username, email } = await userInfo(data.token);
    localStorage.setItem('token', JSON.stringify(data.token));
    return {
      token: data.token,
      username,
      email
    };
  } catch (error) {
    const message = error?.response?.data?.error || error.message;
    const err = new Error(message);
    throw err;
  }
};

export const userInfo = async (token) => {
  try {
    const config = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      }
    };
    const { data } = await axios.get(`${URL}/users/info`, config);
    return data;
  } catch (error) {
    const status = error.response.status;
    if (status === 401 || status === 404) {
      // if status is 401 or 404, remove token from localStorage, because  it's invalid
      localStorage.removeItem('token');
    }
    const message = error?.response?.data?.error || error.message;
    const err = new Error(message);
    throw err;
  }
};

export const updateUser = async (id, user, token) => {
  try {
    const config = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      }
    };
    const { data } = await axios.put(`${URL}/users/${id}`, user, config);
    return data;
  } catch (error) {
    const message = error?.response?.data?.error || error.message;
    const err = new Error(message);
    throw err;
  }
};
