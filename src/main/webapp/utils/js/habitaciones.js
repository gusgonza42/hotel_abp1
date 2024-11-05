// noinspection JSUnresolvedReference





function validarFechas() {
    const fechaEntrada = document.querySelector("input[name='fecha_entrada']").value;
    const fechaSalida = document.querySelector("input[name='fecha_salida']").value;

    if (fechaEntrada && fechaSalida && fechaEntrada >= fechaSalida) {
        Swal.fire({
            icon: 'error',
            title: 'Fechas Incorrectas',
            text: 'La fecha de salida debe ser posterior a la fecha de entrada.',
            confirmButtonText: 'Aceptar'
        });
        return false;
    }
    return true;
}

/**
 * Función para mostrar una alerta de confirmación de reserva.
 */
function mostrarConfirmacionReserva() {
    Swal.fire({
        icon: 'success',
        title: '¡Reserva Completada!',
        text: 'Tu reserva ha sido realizada con éxito.',
        confirmButtonText: 'Aceptar'
    });
}