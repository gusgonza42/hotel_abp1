<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ActividadModel" %>
<%@ page import="model.UserModel" %>

<%
    ArrayList<ActividadModel> actividadesCollection = (ArrayList<ActividadModel>) session.getAttribute("actividades");
    String nombreUsuario = null;
    String rolUsuario = null;
    UserModel usuarioActual = (UserModel) session.getAttribute("usuario");

    if (usuarioActual != null) {
        nombreUsuario = usuarioActual.getNombre();
        rolUsuario = usuarioActual.getRole().name();
    }
%>
<jsp:include page="../../jsp/common/navbaruser.jsp" />

<div class="container-actividades">
    <div class="container mt-5">
        <div class="row">
            <div class="mis-actividades-title">
                <div class="row">
                    <div class="col-md-8">
                        <h1 class="fw-bold">Mis Actividades</h1>
                        <p>Actividades a las que estoy suscrito/a</p>
                    </div>
                    <div class="col-md-4 d-flex justify-content-end align-items-center">
                        <a href="/hotel/reservas" class="btn btn-outline-secondary">Mis Actividades</a>
                    </div>
                </div>
            </div>

            <div class="mb-3">
                <h3 class="actividades-disponibles">Actividades Disponibles</h3>
            </div>

            <div class="row">
                <% if (actividadesCollection != null && !actividadesCollection.isEmpty()) { %>
                <% for (ActividadModel actividad : actividadesCollection) { %>
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <%-- Static image tag commented out --%>
                        <%-- <img src="./utils/imagen/image_default_activities.jpg" class="card-img-top"
                             alt="Imagen de <%= actividad.getNombre_actividad() %>"> --%>
                        <img class="card-img-top actividad-image" alt="Imagen de <%= actividad.getNombre_actividad() %>">

                        <div class="card-body">
                            <h5 class="card-title"><%= actividad.getNombre_actividad() %></h5>
                            <p class="card-text"><%= actividad.getDescripcion() %></p>
                            <p class="card-text"><strong>Precio:</strong> <%= actividad.getPrecio() %> €</p>
                            <p class="card-text"><strong>Cupo:</strong> <%= actividad.getCupo() %></p>
                            <p class="card-text"><strong>Fecha de actividad:</strong> <%= actividad.getFecha_actividad() %></p>

                            <!-- Formulario de reserva de actividad -->
                        <form action="reservaactividad" method="post"> <!-- onsubmit="confirmarReservaActividad(event)" -->
                            <input type="hidden" name="IdUsuario" value="<%= usuarioActual.getId() %>">
                            <input type="hidden" name="IdActividad" value="<%= actividad.getId_actividad() %>">
                            <input type="hidden" name="action" value="add">
                            <button type="submit" class="btn btn-primary">Reservar actividad</button>
                        </form>

                        </div>
                    </div>
                </div>
                <% } %>
                <% } else { %>
                <p class="text-center">No hay actividades disponibles.</p>
                <% } %>
            </div>
        </div>
    </div>
</div>

<script>
const activityImages = [
    "./utils/imagen/image_default_activities.jpg",
  "./utils/imagen/home.jpg",
    "./utils/imagen/image_default_activities.jpg",
    "./utils/imagen/login.jpg",
    "./utils/imagen/login(3).jpg",
    "./utils/imagen/pexels-thorsten-technoman-109353-338504.jpg",
    "./utils/imagen/registro.jpg"
];

function getRandomImages(numImages) {
    const shuffled = [...activityImages].sort(() => Math.random() - 0.5);
    return shuffled.slice(0, numImages);
}

document.addEventListener('DOMContentLoaded', () => {
    const numActividades = <%= actividadesCollection != null ? actividadesCollection.size() : 0 %>;
    const images = getRandomImages(numActividades);
    const imageElements = document.querySelectorAll('.actividad-image');
    imageElements.forEach((img, index) => {
        img.src = images[index % images.length];
    });
});
</script>
