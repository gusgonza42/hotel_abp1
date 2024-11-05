package dao;

import model.ReservasActividadesModel;
import utils.State;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class ReservaActDao extends ConnectionDB {
    public static final String GETALLRESERVAS = "SELECT * FROM reserva_actividades";
    public static final String GETRESERVABYID = "SELECT * FROM reserva_actividades WHERE id_reserva_actividad = ?";
    public static final String ADDRESERVA = "INSERT INTO reserva_actividades(id_usuario,id_actividad,estado,fecha_reserva) VALUES (?,?,?,?)";
    public static final String UPDATERESERVA = "UPDATE reserva_actividades SET id_usuario =? ,id_actividad =? ,estado =? WHERE id_reserva_actividad =?";

    public ArrayList<ReservasActividadesModel> getAllReservas() throws SQLException, ClassNotFoundException {
        ArrayList<ReservasActividadesModel> reservas = new ArrayList<>();
        this.conectar();

        try (PreparedStatement ps = this.getConnection().prepareStatement(GETALLRESERVAS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReservasActividadesModel reserva = new ReservasActividadesModel(
                        rs.getInt("id_reserva_actividad"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_actividad"),
                        State.valueOf(rs.getString("estado"))
                );
                reservas.add(reserva);
            }
        } finally {
            this.desconectar();
        }

        //to check the reserves menu
        for (ReservasActividadesModel reservs : reservas) {
            System.out.println(reservs);
        }
        return reservas;
    }


    public ReservasActividadesModel ReservabyID(int id) throws SQLException, ClassNotFoundException {
        this.conectar();
        ReservasActividadesModel lareserva = null;
        try (PreparedStatement ps = this.getConnection().prepareStatement(GETRESERVABYID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lareserva = new ReservasActividadesModel(
                        rs.getInt("id_reserva_actividad"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_actividad"),
                        State.valueOf(rs.getString("estado"))
                );
            } else {
                return null;
            }
        } finally {
            this.desconectar();
        }
        return lareserva;
    }

    public String add(ReservasActividadesModel lareserva) throws SQLException, ClassNotFoundException {
        LocalDate date = LocalDate.now();
        this.conectar();
        try (PreparedStatement ps = this.getConnection().prepareStatement(ADDRESERVA)) {
            ps.setInt(1, lareserva.getIdUsuario());
            ps.setInt(2, lareserva.getIdActividad());
            ps.setString(3, lareserva.getEstado().toString());
            ps.setString(4, String.valueOf(date));
            int rs = ps.executeUpdate();
            if (rs > 0) {
                System.out.println(lareserva);
                return "Actividad reservado";
            } else {
                return "Error: No se pudo reservar el actividad";
            }
        } finally {
            this.desconectar();
        }
    }

    public String update(ReservasActividadesModel lareserva) throws SQLException, ClassNotFoundException {

        this.conectar();

        try (PreparedStatement ps = this.getConnection().prepareStatement(UPDATERESERVA)) {
            ps.setInt(4, lareserva.getIdReservasActividad());
            ps.setInt(1, lareserva.getIdUsuario());
            ps.setInt(2, lareserva.getIdActividad());
            ps.setString(3, lareserva.getEstado().toString());

            System.out.println(ps.toString());
            int rs = ps.executeUpdate();
            if (rs > 0) {
                System.out.println(lareserva);
                return "RESERVA ACTUALIZADA"; // Successful insertion
            } else {
                return "Error: No se pudo cambiar la reserva"; // Insertion failed
            }
        } finally {
            this.desconectar();
        }
    }
}
