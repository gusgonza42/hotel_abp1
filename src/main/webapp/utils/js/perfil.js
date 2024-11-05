// document.addEventListener("DOMContentLoaded", function() {
//     const form = document.getElementById("perfilForm");
//     if (form) {  // Verificar que el formulario exista
//         form.addEventListener("submit", function(event) {
//             event.preventDefault(); // Evita el envío del formulario

//             Swal.fire({
//                 icon: 'warning',
//                 title: 'Acción no permitida',
//                 text: 'No puedes realizar esta acción en este momento.',
//                 confirmButtonText: 'Volver al inicio',
//                 allowOutsideClick: false
//             }).then((result) => {
//                 if (result.isConfirmed) {
//                     window.location.href = '/hotel/home';
//                 }
//             });
//         });
//     }
// });