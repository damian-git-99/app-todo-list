import Swal from 'sweetalert2';

export const errorMessage = (message) => {
  Swal.fire({
    icon: 'error',
    title: 'Oops...',
    text: message,
    footer: '<a href="">Why do I have this issue?</a>'
  });
};

export const successMessage = (message) => {
  Swal.fire({
    icon: 'success',
    title: '',
    text: message,
    showConfirmButton: false,
    timer: 1500
  });
};
