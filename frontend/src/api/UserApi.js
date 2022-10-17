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
    // todo request for user info
    const { username, email } = await userInfo(data.token);
    // todo save data in localstorage
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
    const { data } = await axios.get('http://127.0.0.1:8080/api/v1/users/info', config);
    return data;
  } catch (error) {
    // handle invalid taken
    // todo check if token is invalid, and if is invalid then remove token from local storage
    // localStorage.removeItem('token');
    const message = error?.response?.data?.error || error.message;
    const err = new Error(message);
    throw err;
  }
};
