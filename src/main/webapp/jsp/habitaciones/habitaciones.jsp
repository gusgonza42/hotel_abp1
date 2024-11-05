<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.HabitacionesModel" %>
<%@ page import="java.util.Base64" %>
<%@ page import="model.UserModel" %>

<%
    ArrayList<HabitacionesModel> habitacionesCollection = (ArrayList<HabitacionesModel>) request.getAttribute("habitaciones");
    UserModel usuarioActual = (UserModel) session.getAttribute("usuario");
    String nombreUsuario = null;
    String rolUsuario = null;

    if (usuarioActual != null) {
        nombreUsuario = usuarioActual.getNombre();
        rolUsuario = usuarioActual.getRole().name();
    }
%>
<jsp:include page="../../jsp/common/navbaruser.jsp" />

<div class="container-habitaciones">
    <div class="container mt-5">
        <div class="row">
            <div class="mis-habitaciones-tittle">
                <div class="row">
                    <div class="col-md-8">
                        <h1 class="fw-bold">Reservas de Habitaciones</h1>
                        <p>Seleccione su habitación y complete la reserva</p>
                    </div>
                </div>
            </div>

            <div class="mb-3">
                <h3 class="habitaciones-disponibles">Habitaciones Disponibles</h3>
            </div>

            <div class="row">
                <%
                    if (habitacionesCollection != null && !habitacionesCollection.isEmpty()) {
                        for (HabitacionesModel habitacion : habitacionesCollection) {
                %>
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <!-- Original image logic commented out -->
                        <%-- <img src="<%= habitacion.getImagen_habitacion() != null ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(habitacion.getImagen_habitacion()) : "./utils/imagen/habitacion-default.jpg" %>"
                             class="card-img-top"
                             alt="Imagen de habitación"> --%>

                        <!-- Placeholder for dynamic images -->
                        <img class="card-img-top habitacion-image" alt="Imagen de habitación">
                        
                        <div class="card-body">
                            <h5 class="card-title"><%= habitacion.getTipo_habitacion().toString() %></h5>
                            <p class="card-text">Precio por noche: <strong><%= habitacion.getPrecio_habitacion() %> €</strong></p>
                            <p class="card-text">Estado: <strong><%= habitacion.getEstado_habitacion() %></strong></p>

                            <form onsubmit="return validarFechas() && mostrarConfirmacionReserva()" action="habitacionReserva" method="post">
                                <input type="hidden" name="IdHabitacion" value="<%= habitacion.getId_habitacion() %>">
                                <!-- input type="hidden" name="id_usuario" value="<%= usuarioActual != null ? usuarioActual.getId() : "" %>" -->
                                <input type="hidden" name="IdUsuario" value="<%= usuarioActual.getId() %>">
                                <input type="hidden" name="action" value="add">
                                <div class="mb-3">
                                    <label for="fecha_entrada" class="form-label">Fecha de Entrada</label>
                                    <input type="date" class="form-control" name="FechaEntrada" required>
                                </div>

                                <div class="mb-3">
                                    <label for="fecha_salida" class="form-label">Fecha de Salida</label>
                                    <input type="date" class="form-control" name="FechaSalida" required>
                                </div>

                                <button type="submit" class="btn btn-primary">Reservar</button>
                            </form>
                        </div>
                    </div>
                </div>
                <%
                    }
                } else {
                %>
                <p class="text-center">No hay habitaciones disponibles.</p>
                <% } %>
            </div>
        </div>
    </div>
</div>

<script>
const imagePaths = [
    "./utils/imagen/habitacion-default.jpg",
    "./utils/imagen/home.jpg",
    "./utils/imagen/image_default_activities.jpg",
    "./utils/imagen/login.jpg",
    "./utils/imagen/login(3).jpg",
    "./utils/imagen/pexels-thorsten-technoman-109353-338504.jpg",
    "./utils/imagen/registro.jpg"
];

function getRandomImages(numImages) {
    const shuffled = [...imagePaths].sort(() => Math.random() - 0.5);
    return shuffled.slice(0, numImages);
}

document.addEventListener('DOMContentLoaded', () => {
    const numHabitaciones = <%= habitacionesCollection != null ? habitacionesCollection.size() : 0 %>;
    const images = getRandomImages(numHabitaciones);
    const imageElements = document.querySelectorAll('.habitacion-image');
    imageElements.forEach((img, index) => {
        img.src = images[index % images.length];
    });
});
</script>
