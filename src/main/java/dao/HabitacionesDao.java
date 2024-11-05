package dao;

import model.HabitacionesModel;
import utils.EstadoHabitacion;
import utils.TipoHabitacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class HabitacionesDao extends ConnectionDB {
    public static final String GETALLHABITACIONES = "SELECT * FROM habitaciones";
    public static final String GETHABITACIONBYID = "SELECT * FROM habitaciones WHERE id_habitacion = ?";
    public static final String UPDATEHABITACION = "UPDATE habitaciones SET tipo_habitacion = ?,imagen =?,precio =? ,estado =? WHERE id_habitacion =?";
    public static final String DELETEHABITACION = "DELETE FROM habitaciones WHERE id_habitacion = ?";
    public static final String ADDEHABITACION = "INSERT INTO habitaciones (tipo_habitacion, imagen, precio, estado) VALUES (?,?,?,?)";

    public ArrayList<HabitacionesModel> getAllHabitaciones() throws SQLException, ClassNotFoundException {
        ArrayList<HabitacionesModel> habitacions = new ArrayList<>();

        //conectar con la base de datos
        this.conectar();

        try (PreparedStatement ps = this.getConnection().prepareStatement(GETALLHABITACIONES)) {
            ResultSet rs = ps.executeQuery();
            // ahora vamos a procesar los datos
            while (rs.next()) {
                HabitacionesModel habitacion = new HabitacionesModel(
                        rs.getInt("id_habitacion"),
                        TipoHabitacion.valueOf(rs.getString("tipo_habitacion")),
                        rs.getBytes("imagen"),
                        rs.getDouble("precio"),
                        EstadoHabitacion.valueOf(rs.getString("estado"))
                );
                habitacions.add(habitacion);
            }
        } finally {
            this.desconectar();
        }
        return habitacions;
    }

    public HabitacionesModel getHabitacionByID(int id) throws SQLException, ClassNotFoundException {
        HabitacionesModel habitacion = null;

        //conectar con la base de datos
        this.conectar();

        try (PreparedStatement ps = this.getConnection().prepareStatement(GETHABITACIONBYID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // ahora vamos a procesar los datos
            if (rs.next()) {
                habitacion = new HabitacionesModel(
                        rs.getInt("id_habitacion"),
                        TipoHabitacion.valueOf(rs.getString("tipo_habitacion")),
                        rs.getBytes("imagen"),
                        rs.getDouble("precio"),
                        EstadoHabitacion.valueOf(rs.getString("estado"))
                );
            }
        } finally {
            this.desconectar();
        }
        return habitacion;
    }

    public String add(HabitacionesModel hab) throws SQLException, ClassNotFoundException {

        this.conectar();
        try (PreparedStatement ps = this.getConnection().prepareStatement(ADDEHABITACION)) {
            ps.setString(1, hab.getTipo_habitacion().toString());
            ps.setString(2, Arrays.toString(hab.getImagen_habitacion()));
            ps.setString(3, String.valueOf(hab.getPrecio_habitacion()));
            ps.setString(4, hab.getEstado_habitacion().toString());

            int rs = ps.executeUpdate();

            if (rs > 0) {
                System.out.println(hab);
                return "Habitacion añadida";
            } else {
                return "Error: no puedo crear la habitacion";
            }
        } finally {
            this.desconectar();
        }
    }

    public String update(HabitacionesModel hab) throws SQLException, ClassNotFoundException {

        this.conectar();
        try (PreparedStatement ps = this.getConnection().prepareStatement(UPDATEHABITACION)) {
            ps.setString(1, hab.getTipo_habitacion().toString());
            ps.setString(2, Arrays.toString(hab.getImagen_habitacion()));
            ps.setString(3, String.valueOf(hab.getPrecio_habitacion()));
            ps.setString(4, hab.getEstado_habitacion().toString());
            ps.setInt(5, hab.getId_habitacion());

            int rs = ps.executeUpdate();

            if (rs > 0) {
                System.out.println(hab);
                return "Habitacion Actualizada";
            } else {
                return "Error: no puedo actualizar la habitacion";
            }
        } finally {
            this.desconectar();
        }
    }

    public String Delete(int id) throws SQLException, ClassNotFoundException {

        this.conectar();
        try (PreparedStatement ps = this.getConnection().prepareStatement(DELETEHABITACION)) {

            ps.setInt(1, id);

            int rs = ps.executeUpdate();

            if (rs > 0) {
                System.out.println(id);
                return "Habitacion Borrada";
            } else {
                return "Error: no puedo borrar la habitacion";
            }
        } finally {
            this.desconectar();
        }
    }
}
