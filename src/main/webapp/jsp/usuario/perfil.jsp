<%@ page import="model.UserModel" %>
<%@ page import="utils.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../../jsp/common/navbaruser.jsp" />

<%
    UserModel usuarioActual = (UserModel) session.getAttribute("usuario");

    if (usuarioActual == null) {
        response.sendRedirect("login");
        return;
    }

    boolean esAdmin = usuarioActual.getRole() == Role.admin;
%>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10">
            <div class="card shadow-lg border-0 rounded-lg">
                <div class="card-header bg-primary text-white text-center">
                    <h3>Perfil de <%= usuarioActual.getNombre() %></h3>
                </div>
                <div class="card-body">
                    <form id="perfilForm">
                        <!-- Nombre -->
                        <div class="form-group row mb-3">
                            <label class="col-md-4 col-form-label font-weight-bold">Nombre:</label>
                            <div class="col-md-8">
                                <input type="text" name="nombre" class="form-control" value="<%= usuarioActual.getNombre() %>" required>
                            </div>
                        </div>

                        <!-- Email -->
                        <div class="form-group row mb-3">
                            <label class="col-md-4 col-form-label font-weight-bold">Email:</label>
                            <div class="col-md-8">
                                <input type="email" name="email" class="form-control" value="<%= usuarioActual.getEmail() %>" required>
                            </div>
                        </div>

                        <!-- Password -->
                        <div class="form-group row mb-3">
                            <label class="col-md-4 col-form-label font-weight-bold">Password:</label>
                            <div class="col-md-8">
                                <input type="password" name="password" class="form-control" placeholder="Ingrese nueva contraseña">
                            </div>
                        </div>

                        <!-- Rol (solo editable para admin) -->
                        <div class="form-group row mb-3">
                            <label class="col-md-4 col-form-label font-weight-bold">Rol:</label>
                            <div class="col-md-8">
                                <% if (esAdmin) { %>
                                <select name="rol" class="form-control">
                                    <option value="CLIENTE" <%= usuarioActual.getRole() == Role.cliente ? "selected" : "" %>>Cliente</option>
                                    <option value="ADMIN" <%= usuarioActual.getRole() == Role.admin ? "selected" : "" %>>Admin</option>
                                    <option value="RECEPCIONISTA" <%= usuarioActual.getRole() == Role.recepcionista ? "selected" : "" %>>Recepcionista</option>
                                </select>
                                <% } else { %>
                                <input type="text" class="form-control" value="<%= usuarioActual.getRole().name() %>" readonly>
                                <% } %>
                            </div>
                        </div>

                        <!-- Estado del usuario (editable solo para admin) -->
                        <div class="form-group row mb-3">
                            <label class="col-md-4 col-form-label font-weight-bold">Estado:</label>
                            <div class="col-md-8">
                                <% if (esAdmin) { %>
                                <select name="estadouser" class="form-control">
                                    <option value="activo" <%= (usuarioActual.getEstadouser() != null && usuarioActual.getEstadouser().name().equals("activo")) ? "selected" : "" %>>Activo</option>
                                    <option value="inactivo" <%= (usuarioActual.getEstadouser() != null && usuarioActual.getEstadouser().name().equals("inactivo")) ? "selected" : "" %>>Inactivo</option>
                                </select>
                                <% } else { %>
                                <input type="text" class="form-control" value="<%= usuarioActual.getEstadouser() != null ? usuarioActual.getEstadouser() : "No especificado" %>" readonly>
                                <% } %>
                            </div>
                        </div>

                        <!-- Fecha de Registro (solo visualización, no editable) -->
                        <div class="form-group row mb-3">
                            <label class="col-md-4 col-form-label font-weight-bold">Fecha de Registro:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" value="<%= usuarioActual.getDate() %>" readonly>
                            </div>
                        </div>

                        <!-- Botón de guardar cambios -->
                        <div class="text-center mt-4">
                            <button type="submit" class="btn btn-success btn-lg">Guardar Cambios</button>
                            <a href="/hotel/home" class="btn btn-secondary btn-lg ml-2">Volver al Inicio</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>