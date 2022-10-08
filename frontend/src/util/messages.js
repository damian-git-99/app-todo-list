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
    position: 'center',
    icon: 'success',
    title: message,
    showConfirmButton: false,
    timer: 1500
  });
};
