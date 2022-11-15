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

export const confirmDialog = (callback) => {
  Swal.fire({
    title: 'Are you sure?',
    text: "You won't be able to revert this!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Yes, delete it!'
  }).then((result) => {
    if (result.isConfirmed) {
      callback();
    }
  });
};
