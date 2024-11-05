<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="java.util.List" %>
    <%@ page import="utils.State" %>
    <%@ page import="model.ReservasHabitacionesModel" %>
    <%@ page import="model.ReservasActividadesModel" %>
                    <%@ page import="model.UserModel" %>
                    <%@ page import="model.ActividadModel" %>

                        <% UserModel loggedUser=(UserModel) session.getAttribute("usuario"); if (loggedUser !=null) {
                            int userId=loggedUser.getId(); 
                            List<ReservasHabitacionesModel> reservasHabitaciones = (List<ReservasHabitacionesModel>)
                                request.getAttribute("reservashab");
                                %>
                                <div class="body">
                                    <jsp:include page="./../common/navbaruser.jsp" />
                                    <div class="reserva-container">
                                        <h2><strong>Tus Reservas</strong></h2>

                                        <h3>Reservas Habitaciones</h3>
                                        <table id="reservashab" class="display">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Habitacion</th>
                                                    <th>Fecha entrada</th>
                                                    <th>Fecha salida</th>
                                                    <th>Estado</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <% if (reservasHabitaciones !=null) { for (ReservasHabitacionesModel reserva : reservasHabitaciones) { if
                                                    (reserva.getIdUsuario()==userId) { %>
                                                    <tr>
                                                        <td>
                                                            <%= reserva.getIdReserva() %>
                                                        </td>
                                                        <td>
                                                            <%= reserva.getIdHabitacion() %>
                                                        </td>
                                                        <td>
                                                            <%= reserva.getFechaEntrada() %>
                                                        </td>
                                                        <td>
                                                            <%= reserva.getFechaSalida() %>
                                                        </td>
                                                        <td>
                                                            <%= reserva.getEstado()%>
                                                        </td>
                                                        <td>
                                                            <% if(reserva.getEstado()==State.reservado){ %>
                                                                <!-- Formulario para Cancelar un reserva -->
                                                                <form action="reservas" method="post" class="d-inline">
                                                                    <input type="hidden" name="reserva"
                                                                        value="habitacion">
                                                                    <input type="hidden" name="IdUsuario"
                                                                        value="<%= reserva.getIdUsuario() %>">
                                                                    <input type="hidden" name="IdHabitacion"
                                                                        value="<%= reserva.getIdHabitacion() %>">
                                                                    <input type="hidden" name="FechaEntrada"
                                                                        value="<%= reserva.getFechaEntrada() %>">
                                                                    <input type="hidden" name="FechaSalida"
                                                                        value="<%= reserva.getFechaSalida() %>">
                                                                    <input type="hidden" name="action"
                                                                        value="cancelado">
                                                                    <input type="hidden" name="IdReserva"
                                                                        value="<%= reserva.getIdReserva() %>">
                                                                    <button type="submit"
                                                                        class="btn btn-danger btn-sm">Cancelar
                                                                        Reserva</button>
                                                                </form>
                                                                <!-- Formulario para Marcar una reserva como completado -->
                                                                <form action="reservas" method="post" class="d-inline">
                                                                    <input type="hidden" name="reserva" value="habitacion">
                                                                    <input type="hidden" name="IdUsuario" value="<%= reserva.getIdUsuario() %>">
                                                                    <input type="hidden" name="IdHabitacion" value="<%= reserva.getIdHabitacion() %>">
                                                                    <input type="hidden" name="FechaEntrada" value="<%= reserva.getFechaEntrada() %>">
                                                                    <input type="hidden" name="FechaSalida"
                                                                        value="<%= reserva.getFechaSalida() %>">
                                                                    <input type="hidden" name="action"
                                                                        value="completado">
                                                                    <input type="hidden" name="IdReserva"
                                                                        value="<%= reserva.getIdReserva() %>">
                                                                    <button type="submit"
                                                                        class="btn btn-success btn-sm">Finalizar
                                                                        Reserva</button>
                                                                </form>
                                                                <% } else { %>
                                                                    <p>No Puedes modificar la reserva</p>
                                                                    <% }%>
                                                        </td>
                                                    </tr>
                                                    <% } } } else { %>
                                                        <tr>
                                                            <td colspan="5">No hay reservas de habitaciones.</td>
                                                        </tr>
                                                        <% } %>
                                            </tbody>
                                        </table>

                                        <hr>

                                        <h3>Reservas Actividades</h3>
                                        <table id="reservasact" class="display">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Actividad</th>
                                                    <th>Estado</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <% // Retrieve the list of activity reservations
                                                    List<ReservasActividadesModel> reservasActividades = (List <ReservasActividadesModel>) request.getAttribute("reservasact");
                                                    List<ActividadModel> actividades = (List <ActividadModel>) request.getAttribute("actividades");

                                                        if (reservasActividades != null) {
                                                        for (ReservasActividadesModel reserva : reservasActividades) {
                                                        if (reserva.getIdUsuario() == userId) {
                                                        %>
                                                        <tr>
                                                            <td>
                                                                <%= reserva.getIdReservasActividad() %>
                                                            </td>
                                                            <td>
                                                            <% for (ActividadModel actividad : actividades) {
                                                                if(reserva.getIdActividad() == actividad.getId_actividad()){
                                                                %>
                                                                <%= actividad.getNombre_actividad() %>
                                                                  <% }} %>
                                                            </td>
                                                            <td>
                                                                <%= reserva.getEstado() %>
                                                            </td>
                                                            <td>
                                                                <% if(reserva.getEstado()==State.reservado){ %>
                                                                    <!-- Formulario para Cancelar un reserva -->
                                                                    <form action="reservas" method="post" class="d-inline">
                                                                        <input type="hidden" name="reserva" value="actividad">
                                                                        <input type="hidden" name="IdUsuario" value="<%= reserva.getIdUsuario() %>">
                                                                        <input type="hidden" name="IdActividad" value="<%= reserva.getIdActividad() %>">
                                                                       
                                                                        <input type="hidden" name="action" value="cancelado">
                                                                        <input type="hidden" name="IdReserva" value="<%= reserva.getIdReservasActividad() %>">
                                                                        <button type="submit" class="btn btn-danger btn-sm">Cancelar Reserva</button>
                                                                    </form>
                                                                    <!-- Formulario para Marcar una reserva como completado -->
                                                                    <form action="reservas" method="post" class="d-inline">
                                                                        <input type="hidden" name="reserva" value="actividad">
                                                                        <input type="hidden" name="IdUsuario" value="<%= reserva.getIdUsuario() %>">
                                                                        <input type="hidden" name="IdActividad" value="<%= reserva.getIdActividad() %>">
                                                                       
                                                                        <input type="hidden" name="action"  value="completado">
                                                                        <input type="hidden" name="IdReserva"value="<%= reserva.getIdReservasActividad() %>">
                                                                        <button type="submit" class="btn btn-success btn-sm">Finalizar Reserva</button>
                                                                    </form>
                                                                    <% } else { %>
                                                                        <p>No Puedes modificar la reserva</p>
                                                                        <% }%>
                                                            </td>
                                                        </tr>
                                                        <% } } } else { %>
                                                            <tr>
                                                                <td colspan="5">No hay reservas de actividades.</td>
                                                            </tr>
                                                            <% } %>
                                            </tbody>
                                        </table>


                                        <% } else { %>
                                            <div class="container mt-5 d-flex justify-content-center">
                                                <div class="container-error">
                                                    <div class="card text-center shadow-lg error-card">
                                                        <div class="card-body">
                                                            <div class="error-icon mb-4">
                                                                🚨🚀
                                                            </div>
                                                            <h1 class="display-5">¡No estas Logeado!</h1><br>
                                                            <!-- Mensaje de error genérico -->
                                                            <p class="error-message">Lo sentimos, ha ocurrido un
                                                                problema
                                                                inesperado. Por favor, intenta nuevamente más tarde.</p>
                                                            <!-- Botón de regreso a la página anterior con JS -->
                                                            <a href="/hotel/login"> <button
                                                                    class="btn btn-back btn-lg mt-3">Login</button><br></a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <% } %>
                                    </div>
                                </div>