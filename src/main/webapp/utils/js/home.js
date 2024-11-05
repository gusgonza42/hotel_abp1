// noinspection JSUnresolvedReference

    function showReceptionAlert() {
        Swal.fire({
            icon: 'info',
            title: 'Contacto con Recepción',
            text: 'En breve nos comunicaremos con usted. ¡Gracias por su paciencia!',
            confirmButtonText: 'OK'
        });
    }

    function showIncidenciaNotification() {
        Swal.fire({
            title: 'Reportar Incidencia',
            input: 'textarea',
            inputLabel: 'Describa el problema que está experimentando',
            inputPlaceholder: 'Escribe aquí los detalles de la incidencia...',
            confirmButtonText: 'Notificar',
            showCancelButton: true,
            cancelButtonText: 'Cancelar',
            inputValidator: (value) => {
                if (!value) {
                    return 'Por favor, escribe una descripción de la incidencia';
                }
            }
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    icon: 'success',
                    title: 'Incidencia Notificada',
                    text: 'Hemos recibido su notificación. Nuestro equipo lo atenderá a la brevedad.',
                    confirmButtonText: 'OK'
                });
            }
        });
    }

    function showReservaWarning() {
        Swal.fire({
            icon: 'warning',
            title: 'Reserva no disponible',
            text: 'No puedes realizar la reserva en este momento. Por favor, inténtalo más tarde.',
            confirmButtonText: 'Aceptar'
        });
    }