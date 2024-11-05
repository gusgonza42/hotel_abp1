<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.UserModel" %>
<%@ page import="model.ActividadModel" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Random" %>

<%
    UserModel usuarioActual = (UserModel) session.getAttribute("usuario");

    ActividadModel actividadRecomendada = null;
    ArrayList<ActividadModel> listaActividades = (ArrayList<ActividadModel>) session.getAttribute("actividades");
    String nombreUsuario = null;
    String rolUsuario = null;

    if (usuarioActual != null) {
        nombreUsuario = usuarioActual.getNombre();
        rolUsuario = usuarioActual.getRole().name();
    }
    if (listaActividades != null && !listaActividades.isEmpty()) {
        Random random = new Random();
        actividadRecomendada = listaActividades.get(random.nextInt(listaActividades.size()));
    }
%>

<jsp:include page="../../jsp/common/navbaruser.jsp"/>

<div class="container-home-user">
    <div class="container mt-4">
        <section class="mb-4">
            <h3 class="text-center mb-4">Servicios Populares</h3>
            <div class="card shadow-sm">
                <div class="card-body">
                    <div class="row text-center">
                        <div class="col-lg-4 col-md-6 mb-2">
                            <a href="/hotel/habitaciones" class="btn btn-primary w-100">Reservar Habitación</a>
                        </div>
                        <div class="col-lg-4 col-md-6 mb-2">
                            <button onclick="showReceptionAlert()" class="btn btn-success w-100">Contactar Recepción
                            </button>
                        </div>
                        <div class="col-lg-4 col-md-6 mb-2">
                            <a href="https://www.boe.es/buscar/act.php?id=BOE-A-2006-13087&p=20221228&tn=2"
                               class="btn btn-warning w-100 text-dark"
                               target="_blank"
                               rel="noopener noreferrer">
                                Normas del Hotel
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <div class="container">
            <div class="row">
                <!-- Galería de Fotos -->
                <div class="col-md-6 col-lg-6 mb-4">
                    <h3>Galería de Fotos</h3>
                    <div id="demo" class="carousel slide" data-bs-ride="carousel" data-bs-interval="3000">
                        <div class="carousel-indicators">
                            <button type="button" data-bs-target="#demo" data-bs-slide-to="0" class="active"></button>
                            <button type="button" data-bs-target="#demo" data-bs-slide-to="1"></button>
                            <button type="button" data-bs-target="#demo" data-bs-slide-to="2"></button>
                        </div>
                        <div class="carousel-inner">
                            <div class="carousel-item active">
                                <img src="./utils/imagen/home.jpg" alt="Home Imagen" class="d-block w-100 mx-auto">
                            </div>
                            <div class="carousel-item">
                                <img src="./utils/imagen/image_default_activities.jpg" alt="Home imagen 3"
                                     class="d-block w-100 mx-auto">
                            </div>
                            <div class="carousel-item">
                                <img src="./utils/imagen/login.jpg" alt="Home imagen 2" class="d-block w-100 mx-auto">
                            </div>
                        </div>
                        <button class="carousel-control-prev" type="button" data-bs-target="#demo" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon"></span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#demo" data-bs-slide="next">
                            <span class="carousel-control-next-icon"></span>
                        </button>
                    </div>
                </div>

                <!-- Actividades Recomendadas -->
                <div class="col-md-6 col-lg-6 mb-4">
                    <h3>Actividades Recomendadas</h3>
                    <div class="card text-center">
                        <div class="card-body">
                            <p style="font-size: larger">
                                <strong><%= actividadRecomendada != null ? actividadRecomendada.getNombre_actividad() : "No hay actividades disponibles" %>
                                </strong>
                            </p>
                            <p><%= actividadRecomendada != null ? actividadRecomendada.getDescripcion() : "" %>
                            </p>
                            <p><%= actividadRecomendada != null ? actividadRecomendada.getFecha_actividad() : "" %>
                                <%= actividadRecomendada != null ? actividadRecomendada.getPrecio() : "" %>
                            </p>
                            <button onclick="showReservaWarning()" class="btn btn-success w-100">Reservar actividad</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Incidencias -->
            <div class="row">
                <div class="col-12">
                    <div class="mb-12">
                        <h3>Notificar una Incidencia</h3>
                        <div class="card">
                            <div class="card-body">
                                <p>Si experimentas algún problema durante tu estancia, por favor notifícalo aquí para
                                    que podamos ayudarte.</p>
                                <button onclick="showIncidenciaNotification()" class="btn btn-danger">Reportar
                                    Incidencia
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>