<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.UserModel" %>
<%@ page import="utils.Role" %>
<%@ page import="utils.EstadoHabitacion" %>
<%@ page import="utils.TipoHabitacion" %>
<%@ page import="utils.Estadouser" %>

<jsp:include page="../../jsp/common/navbaruser.jsp" />

<%
    UserModel usuarioActual = (UserModel) session.getAttribute("usuario");
    Role[] roles = (Role[]) request.getAttribute("roles");
    EstadoHabitacion[] estados = (EstadoHabitacion[]) request.getAttribute("Estado");
    TipoHabitacion[] tipos = (TipoHabitacion[]) request.getAttribute("tipo_habitacion");
    ArrayList<UserModel> usuariosList = (ArrayList<UserModel>) request.getAttribute("usuarios");



    // Verificamos que usuarioActual no sea null y si es admin
    boolean esAdmin = usuarioActual != null && usuarioActual.getRole() == Role.admin;

    if (usuarioActual == null) {
        response.sendRedirect("login");
        return;
    }
%>

<div class="container mt-5">
    <h1 class="text-center mb-5">Bienvenido al Panel de Administración</h1>
    <p class="text-center text-muted"><%= request.getAttribute("ERROR") != null ? request.getAttribute("ERROR") : "" %></p>

    <div class="row">
        <!-- Formulario para actualizar perfil del usuario -->
        <div class="col-md-6 mb-4">
            <div class="card h-100">
                <div class="card-header text-center bg-primary text-white">
                    <h3>Perfil de <%= usuarioActual.getNombre() %></h3>
                </div>
                <div class="card-body">
                    <form id="perfilForm" method="post" action="usuarios"> <%-- onsubmit="ShowAlert(event)"                  --%>
                    <input type="hidden" name = "action" value="update">
                    <input type="hidden" name = "id" value="<%= usuarioActual.getId() %>">
                        <div class="mb-3">
                            <label class="form-label">Nombre:</label>
                            <input type="text" name="nombre" class="form-control" value="<%= usuarioActual.getNombre() %>" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Email:</label>
                            <input type="email" name="email" class="form-control" value="<%= usuarioActual.getEmail() %>" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Contraseña:</label>
                            <input type="password" name="password" class="form-control" placeholder="Nueva contraseña" > <!-- value="<%= usuarioActual.getPassword() %>" -->
                        </div>
                        <% if (esAdmin) { %>
                        <div class="mb-3">
                            <label class="form-label">Rol:</label>
                            <%-- <select name="rol" class="form-control">
                                <option value="CLIENTE" <%= usuarioActual.getRole() == Role.cliente ? "selected" : "" %>>Cliente</option>
                                <option value="ADMIN" <%= usuarioActual.getRole() == Role.admin ? "selected" : "" %>>Admin</option>
                                <option value="RECEPCIONISTA" <%= usuarioActual.getRole() == Role.recepcionista ? "selected" : "" %>>Recepcionista</option>
                            </select> --%>


                        <select name="rol" id="role">
                            <%
                                if (roles != null) {
                                    for (Role role : roles) { 
                            %>
                                <option value="<%= role.name() %>"><%= role.name()%></option>
                            <% 
                                    }
                                } 
                            %>
                        </select>

                        </div>
                        <% } else { %>
                        <div class="mb-3">
                            <label class="form-label">Rol:</label>
                            <input type="text" name="rol" class="form-control" value="<%= usuarioActual.getRole().toString() %>" readonly>
                        </div>
                        <% } %>
                        <button type="submit" class="btn btn-success w-100">Actualizar Perfil</button>
                    </form>
                </div>
            </div>
        </div>

        <% if (esAdmin) { %>
        <!-- Formulario para agregar una habitación -->
        <div class="col-md-6 mb-4">
            <div class="card h-100">
                <div class="card-header text-center bg-primary text-white">
                    <h3>Agregar Habitación</h3>
                </div>
                <div class="card-body">
                    <form id="agregarHabitacionForm" action="habitaciones"  method ="post" > <!-- onsubmit="ShowAlert(event, 'agregarHabitacionForm')" -->
                        <div class="mb-3">
                            <label class="form-label">Tipo de Habitación:</label>
                            <!--select name="tipo_habitacion" class="form-control">
                                <option value="sencilla">Sencilla</option>
                                <option value="doble">Doble</option>
                                <option value="suite">Suite</option>
                            </select -->

                            <select name="tipoHabitacion" id="tipo_habitacion">
                            <%
                                if (tipos != null) {
                                    for (TipoHabitacion tipo : tipos) { 
                            %>
                                <option value="<%= tipo.name() %>"><%= tipo.name() %></option>
                            <% 
                                    }
                                } 
                            %>
                        </select>

                        </div>
                        <div class="mb-3">
                            <label class="form-label">Precio:</label>
                            <input type="number" name="precio" class="form-control" required>
                            <input type="hidden" name="action" class="form-control" value="add">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Estado:</label>
                            <!-- select name="estado" class="form-control">
                                <option value="disponible">Disponible</option>
                                <option value="ocupada">Ocupada</option>
                                <option value="mantenimiento">Mantenimiento</option>
                            </select-->

                               <select name="estadoHabitacion" id="estado">
                            <%
                                if (estados != null) {
                                    for (EstadoHabitacion estado : estados) { 
                            %>
                                <option value="<%= estado.name() %>"><%= estado.name() %></option>
                            <% 
                                    }
                                } 
                            %>
                        </select>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Agregar Habitación</button>
                    </form>
                </div>
            </div>
        </div>

        <!-- Formulario para agregar una actividad -->
        <div class="col-md-6 mb-4">
            <div class="card h-100">
                <div class="card-header text-center bg-primary text-white">
                    <h3>Agregar Actividad</h3>
                </div>
                <div class="card-body">
                    <form id="agregarActividadForm" action="actividades" method="post">
                        <div class="mb-3">
                            <label class="form-label">Nombre de la Actividad:</label>
                            <input type="text" name="nombre_actividad" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Descripción:</label>
                            <textarea name="descripcion" class="form-control" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Precio:</label>
                            <input type="number" name="precio" class="form-control" step="0.1" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Cupo:</label>
                            <input type="number" name="cupo" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Fecha de Actividad:</label>
                            <input type="date" name="fecha_actividad" class="form-control" required>
                            <input type="hidden" name="action" value="add">
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Agregar Actividad</button>
                    </form>
                </div>
            </div>
        </div>

        <!-- Listado de usuarios -->
        <div class="col-md-12 mb-4">
            <div class="card h-100">
                <div class="card-header text-center bg-primary text-white">
                    <h3>Lista de Usuarios</h3>
                </div>
                <div class="card-body">

<table id="users" class="display">
    <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Email</th>
            <th>Rol</th>
            <th>Accion</th>
        </tr>
    </thead>
    <tbody>
        <% 
            if (usuariosList != null && !usuariosList.isEmpty()) { 
                for (UserModel usuario : usuariosList) { 
                    if(usuario.getId()!=usuarioActual.getId()){
        %>
            <tr>
                <td><%= usuario.getId() %></td>
                <td><%= usuario.getNombre() %></td>
                <td><%= usuario.getEmail() %></td>
                <td><%= usuario.getRole().name() %></td>
                <td>
                    <% if (usuario.getEstadouser() == Estadouser.activo) { %>
                        <form action="usuarios" method="post" class="d-inline">
                            <input type="hidden" name="action" value="inactivar">
                            <input type="hidden" name="nombre" value="<%= usuario.getNombre() %>">
                            <input type="hidden" name="email" value="<%= usuario.getEmail() %>">
                            <input type="hidden" name="rol" value="<%= usuario.getRole().name() %>">
                            <input type="hidden" name="id" value="<%= usuario.getId() %>">
                            <button type="submit" class="btn btn-danger btn-sm">Desactivar</button>
                        </form>
                    <% } else { %>
                        <p>Desactivado</p>
                    <% }} %>
                </td>
            </tr>
        <% 
                }
            } else { 
        %>
            <tr>
                <td colspan="5">No hay usuarios registrados.</td>
            </tr>
        <% } %>
    </tbody>
</table>

                
                    <form id="mostrarUsuariosForm" onsubmit="ShowAlert(event)">
                        <button type="submit" class="btn btn-secondary w-100">Ver Usuarios</button>
                    </form>
                </div>
            </div>
        </div>
        <% } %>
    </div>
</div>