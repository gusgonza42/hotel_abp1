<%@ page import="java.util.List" %>
<%@ page import="model.ReservasHabitacionesModel" %>


<h2> <strong> Tus Reservas </h2>

<h3>Reservas Habitacions</h3>
<table id="reservas" class="display">
    <thead>
        <tr>
            <th>ID</th>
            <th>Habitacion</th>
            <th>Fecha entrada</th>
            <th>Fecha salida</th>
            <th>Estado</th>
        </tr>
    </thead>
    <tbody>
        <%  
            List<ReservasHabitacionesModel> reservas = (List<ReservasHabitacionesModel>) request.getAttribute("reservas");
            for (ReservasHabitacionesModel reserva : reservas) {
        %>
        <tr>
            <td><%= reserva.getIdReserva() %></td>
            <td><%= reserva.getIdHabitacion() %></td>
            <td><%= reserva.getFechaEntrada() %></td>
            <td><%= reserva.getFechaSalida() %></td>
            <td><%= reserva.getEstado() %></td>
        </tr>
        <% 
            }
        %>
    </tbody>
</table>


<script>
    $(document).ready(function() {
        $('#reservas').DataTable();
    });
</script>