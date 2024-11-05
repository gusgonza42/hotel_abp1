// Función para mostrar confirmación al reservar una actividad
function confirmarReservaActividad(event) {
    event.preventDefault();
    Swal.fire({
        icon: 'success',
        title: 'Actividad Reservada',
        text: 'Has reservado la actividad con éxito.',
        confirmButtonText: 'Aceptar'
    }).then(() => {
        window.location.href = "actividades";
    });
}