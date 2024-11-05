// noinspection JSUnresolvedReference

document.addEventListener("DOMContentLoaded", function () {
    const registroForm = document.getElementById('registroForm');

    // Verificar si el formulario de registro existe
    if (registroForm) {
        registroForm.addEventListener('submit', function (event) {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirm-password').value;

            if (password !== confirmPassword) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Advertencia',
                    text: 'Las contraseñas no coinciden. Por favor, verifica.',
                    confirmButtonText: 'Aceptar'
                });
                event.preventDefault(); // Evita que el formulario se envíe
            }
        });
    }
});