<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container mt-5 d-flex justify-content-center">
    <div class="container-error">
        <div class="card text-center shadow-lg error-card">
            <div class="card-body">
                <div class="error-icon mb-4">
                    🚨🚀
                </div>
                <h1 class="display-5">¡Algo salió mal!</h1><br>

                <!-- Mostrar el mensaje de error específico si existe -->
                <%
                    String errorMessage = (String) request.getAttribute("ERROR");
                    if (errorMessage != null) {
                %>
                <p class="lead error-message"><%= errorMessage %>
                </p><br>
                <% } %>

                <!-- Mensaje de error genérico -->
                <p class="error-message">Lo sentimos, ha ocurrido un problema inesperado. Por favor, intenta nuevamente
                    más tarde.</p>

                <!-- Botón de regreso a la página anterior con JS -->
                <button onclick="window.history.back();" class="btn btn-back btn-lg mt-3">Volver a la página anterior
                </button>
                <br>
            </div>
        </div>
    </div>
</div>