<%-- suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.UserModel" %>
<%@ page import="utils.Role" %>

<%-- Obtenemos el usuario actual de la sesión --%>
<%
    UserModel usuarioActual = (UserModel) session.getAttribute("usuario");
    String nombreUsuario = (usuarioActual != null) ? usuarioActual.getNombre() : "Usuario";
    boolean esAdmin = (usuarioActual != null && usuarioActual.getRole() == Role.admin);
%>

<nav class="navbar navbar-expand-lg navbar-dark py-3 shadow">
    <div class="container">
        <a class="navbar-brand d-flex align-items-center" href="/hotel/home">
            <img src="./utils/imagen/logo.png" alt="Logo" width="40" height="40" class="d-inline-block me-2">
            <span class="fw-bold">SUN HOTEL</span>
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link <% if(request.getRequestURI().contains("/home")) { %>active<% } %>" href="/hotel/home">Inicio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <% if(request.getRequestURI().contains("/reservas")) { %>active<% } %>" href="/hotel/reservas">Reservas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <% if(request.getRequestURI().contains("/habitaciones")) { %>active<% } %>" href="/hotel/habitaciones">Habitaciones</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <% if(request.getRequestURI().contains("/actividades")) { %>active<% } %>" href="/hotel/actividades">Actividades</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <% if(request.getRequestURI().contains("/perfil")) { %>active<% } %>" href="/hotel/perfil">Perfil</a>
                </li>

                <%-- Mostrar enlace Admin solo si el usuario es administrador --%>
                <% if (esAdmin) { %>
                <li class="nav-item">
                    <a class="nav-link <% if(request.getRequestURI().contains("/admin")) { %>active<% } %>" href="/hotel/admin">Admin</a>
                </li>
                <% } %>
            </ul>

            <ul class="navbar-nav ms-auto align-items-center">
                <%-- Saludo de usuario --%>
                <li class="nav-item me-3">
                    <span class="navbar-text text-light">
                        Bienvenido/a, <strong><%= nombreUsuario %></strong>
                    </span>
                </li>

                <%-- Botón de cierre de sesión --%>
                <li class="nav-item">
                    <a class="btn btn-outline-light" href="/hotel/logout">Cerrar sesión</a>
                </li>
            </ul>
        </div>
    </div>
</nav>