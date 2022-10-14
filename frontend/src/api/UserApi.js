import axios from 'axios';

export const signup = async (user) => {
  try {
    const { data } = await axios.post('http://127.0.0.1:8080/api/v1/users', user);
    return data;
  } catch (error) {
    const message = error?.response?.data?.error || error.message;
    const err = new Error(message);
    throw err;
  }
};

export const login = async (user) => {
  try {
    const { data } = await axios.post('http://127.0.0.1:8080/api/v1/auth', user);
    // todo save data in localstorage
    localStorage.setItem('user', JSON.stringify(data));
    return data;
  } catch (error) {
    const message = error?.response?.data?.error || error.message;
    const err = new Error(message);
    throw err;
  }
};
