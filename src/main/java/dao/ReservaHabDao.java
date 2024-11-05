package dao;

import model.ReservasHabitacionesModel;
import utils.State;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class ReservaHabDao extends ConnectionDB {
    public static final String GETALLRESERVAS = "SELECT * FROM reserva_habitaciones";
    public static final String GETRESERVABYID = "SELECT * FROM reserva_habitaciones WHERE id_reserva_habitacion = ?";
    public static final String ADDRESERVA = "INSERT INTO reserva_habitaciones(id_usuario,id_habitacion,fecha_entrada,fecha_salida,estado,fecha_reserva) VALUES (?,?,?,?,?,?)";
    public static final String UPDATERESERVA = "UPDATE reserva_habitaciones SET id_usuario =? ,id_habitacion =? ,fecha_entrada =? ,fecha_salida =?,estado =? WHERE id_reserva_habitacion =?";

    public ArrayList<ReservasHabitacionesModel> getAllReservas() throws SQLException, ClassNotFoundException {
        ArrayList<ReservasHabitacionesModel> reservas = new ArrayList<>();
        this.conectar();

        try (PreparedStatement ps = this.getConnection().prepareStatement(GETALLRESERVAS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReservasHabitacionesModel reserva = new ReservasHabitacionesModel(
                        rs.getInt("id_reserva_habitacion"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_habitacion"),
                        State.valueOf(rs.getString("estado")),
                        rs.getString("fecha_entrada"),
                        rs.getString("fecha_salida")
                );
                reservas.add(reserva);
            }
        } finally {
            this.desconectar();
        }

        //to check the reserves menu
        for (ReservasHabitacionesModel reservs : reservas) {
            System.out.println(reservs);
        }
        return reservas;
    }


    public ReservasHabitacionesModel ReservabyID(int id) throws SQLException, ClassNotFoundException {
        this.conectar();
        ReservasHabitacionesModel lareserva = null;
        try (PreparedStatement ps = this.getConnection().prepareStatement(GETRESERVABYID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lareserva = new ReservasHabitacionesModel(
                        rs.getInt("id_reserva_habitacion"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_habitacion"),
                        State.valueOf(rs.getString("estado")),
                        rs.getString("fecha_entrada"),
                        rs.getString("fecha_salida")

                );
            } else {
                return null;
            }
        } finally {
            this.desconectar();
        }


        return lareserva;
    }

    public String add(ReservasHabitacionesModel lareserva) throws SQLException, ClassNotFoundException {
        LocalDate date = LocalDate.now();
        this.conectar();
        try (PreparedStatement ps = this.getConnection().prepareStatement(ADDRESERVA)) {
            ps.setInt(1, lareserva.getIdUsuario());
            ps.setInt(2, lareserva.getIdHabitacion());
            ps.setString(3, lareserva.getFechaEntrada());
            ps.setString(4, lareserva.getFechaSalida());
            ps.setString(5, lareserva.getEstado().toString());
            ps.setString(6, String.valueOf(date));
            int rs = ps.executeUpdate();
            if (rs > 0) {
                System.out.println(lareserva);
                return "Habitacion reservada"; // Successful insertion
            } else {
                return "Error: No se pudo reservar la habitacion"; // Insertion failed
            }
        } finally {
            this.desconectar();
        }
    }

    public String update(ReservasHabitacionesModel lareserva) throws SQLException, ClassNotFoundException {

        this.conectar();

        try (PreparedStatement ps = this.getConnection().prepareStatement(UPDATERESERVA)) {
            ps.setInt(6, lareserva.getIdReserva());
            ps.setInt(1, lareserva.getIdUsuario());
            ps.setInt(2, lareserva.getIdHabitacion());
            ps.setString(3, lareserva.getFechaEntrada());
            ps.setString(4, lareserva.getFechaSalida());
            ps.setString(5, lareserva.getEstado().toString());

            System.out.println(ps.toString());
            int rs = ps.executeUpdate();
            if (rs > 0) {
                System.out.println(lareserva);
                return "RESERVA ACTUALIZADA";
            } else {
                return "Error: No se pudo cambiar la reserva";
            }
        } finally {
            this.desconectar();
        }
    }
}
