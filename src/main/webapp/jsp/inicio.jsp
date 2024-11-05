<%--suppress ALL --%>

<%--
    Este archivo JSP define la estructura principal de la página de inicio.
    Incluye la barra de navegación común y un mensaje de bienvenida.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="inicio">
    <!-- Incluir la barra de navegación común para la página de inicio -->
    <jsp:include page="./common/navbarInicio.jsp"/>

    <div class="container-inicio">
        <!-- Mensaje de bienvenida para el SUN☀ HOTEL -->
        <h1>
            Bienvenido/a a <strong>SUN </strong>☀️ HOTEL
        </h1>
        <!-- Botón para navegar a la página de login -->
        <button class="button">
            <a href="/hotel/login" class="login-button">Login</a>
        </button>
    </div>
</div>