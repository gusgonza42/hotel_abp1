function ShowAlert(event) {

    event.preventDefault();

    Swal.fire({
        icon: 'warning',
        title: 'Función no disponible',
        text: 'Esta función estará disponible más tarde. Por favor, inténtalo de nuevo más tarde.',
        confirmButtonText: 'Aceptar',
        customClass: {
            confirmButton: 'btn btn-primary' // Usar Bootstrap para el botón de confirmación
        },
        buttonsStyling: false // Evita los estilos predeterminados de SweetAlert en botones
    });
}


jQuery(document).ready(function () {
    jQuery('#users').DataTable();
});



// // Function to show alert and handle form submission
// function ShowAlert(event, formId) {
//     event.preventDefault(); // Prevent the default form submission

//     let swalOptions = {
//         icon: 'warning',
//         confirmButtonText: 'Aceptar',
//         customClass: {
//             confirmButton: 'btn btn-primary'
//         },
//         buttonsStyling: false
//     };

//     // Customize the SweetAlert based on the form ID
//     switch (formId) {
//         case 'perfilForm':
//             swalOptions.title = 'Actualización de Perfil';
//             swalOptions.text = 'La actualización de tu perfil se procesará ahora.';
//             break;
//         case 'agregarHabitacionForm':
//             swalOptions.title = 'Agregar Habitación';
//             swalOptions.text = 'Se está agregando la nueva habitación. Por favor, espera.';
//             break;
//         case 'agregarActividadForm':
//             swalOptions.title = 'Agregar Actividad';
//             swalOptions.text = 'Se está registrando la nueva actividad. Por favor, espera.';
//             break;
//         default:
//             console.error('Form ID not recognized');
//             return;
//     }

//     // Show the SweetAlert confirmation
//     Swal.fire(swalOptions).then((result) => {
//         if (result.isConfirmed) {
//             const form = document.getElementById(formId);
//             if (form) {
//                 // Set the correct action and method based on form ID
//                 switch (formId) {
//                     case 'perfilForm':
//                         form.action = '/usuarios'; // Set the action correctly
//                         break;
//                     case 'agregarHabitacionForm':
//                         form.action = '/agregarHabitacion'; // Set the action correctly
//                         break;
//                     case 'agregarActividadForm':
//                         form.action = '/agregarActividad'; // Set the action correctly
//                         break;
//                     default:
//                         console.error('Form ID not recognized');
//                         return;
//                 }

//                 // Use the Fetch API to submit the form data
//                 fetch(form.action, {
//                     method: 'POST',
//                     body: new FormData(form)
//                 })
//                 .then(response => {
//                     if (response.ok) {
//                         // Handle successful response
//                         console.log('Form submitted successfully');
//                         // Maintain the same link
//                         history.replaceState(null, null, window.location.href);
//                     } else {
//                         // Handle errors here
//                         console.error('Error submitting form:', response.statusText);
//                     }
//                 })
//                 .catch(error => {
//                     console.error('Error:', error);
//                 });
//             }
//         }
//     });
// }
