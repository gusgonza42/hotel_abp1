package dao;

import model.ActividadModel;
import utils.Constantes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActividadesDao extends ConnectionDB {
    private static final String GET_ALL_ACTIVIDADES = "SELECT id_actividad, nombre_actividad, descripcion, imagen, precio, cupo, fecha_actividad FROM actividades";
    private static final String GET_ACTIVIDADES_BY_ID = "SELECT id_actividad, nombre_actividad, descripcion, imagen, precio, cupo, fecha_actividad FROM actividades WHERE id_actividad = ?";
    private static final String INSERT_ACTIVIDADES = "INSERT INTO actividades (nombre_actividad, descripcion, imagen, precio, cupo, fecha_actividad) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ACTIVIDAD_BY_ID = "UPDATE actividades SET nombre_actividad=?, descripcion=?, imagen=?, precio=?, cupo=?, fecha_actividad=? WHERE id_actividad=?";
    private static final String DELETE_ACTIVITIE_BY_ID = "DELETE FROM actividades WHERE id_actividad =?";
    private static final String CHECK_NUM_ID = "SELECT COUNT(*) FROM actividades WHERE id_actividad =?";

    public ArrayList<ActividadModel> getAllActividades() throws SQLException {
        ArrayList<ActividadModel> actividades = new ArrayList<>();
        PreparedStatement ps = this.connection.prepareStatement(GET_ALL_ACTIVIDADES);

        Constantes.printMssg(ps.toString());

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ActividadModel actividadActual = new ActividadModel(
                    rs.getInt("id_actividad"),
                    rs.getString("nombre_actividad"),
                    rs.getString("descripcion"),
                    rs.getBytes("imagen"),
                    (int) rs.getInt("precio"),
                    rs.getInt("cupo"),
                    rs.getString("fecha_actividad")
            );
            actividades.add(actividadActual);
        }

        return actividades;
    }

    public ActividadModel getByIdActividades(int id) throws SQLException {
        ActividadModel actividadById = new ActividadModel();
        PreparedStatement ps = this.connection.prepareStatement(GET_ACTIVIDADES_BY_ID);
        ps.setInt(1, id);


        Constantes.printMssg(ps.toString());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            actividadById.setId_actividad(rs.getInt("id_actividad"));
            actividadById.setNombre_actividad(rs.getString("nombre_actividad"));
            actividadById.setDescripcion(rs.getString("descripcion"));
            actividadById.setImagen(rs.getBytes("imagen"));
            actividadById.setPrecio(rs.getDouble("precio"));
            actividadById.setCupo(rs.getInt("cupo"));
            actividadById.setFecha_actividad(rs.getString("fecha_actividad"));
        }
        return actividadById;
    }

    public String insertActividades(ActividadModel actividadInsert) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(INSERT_ACTIVIDADES);
        ps.setString(1, actividadInsert.getNombre_actividad());
        ps.setString(2, actividadInsert.getDescripcion());
        ps.setBytes(3, actividadInsert.getImagen());
        ps.setDouble(4, actividadInsert.getPrecio());
        ps.setInt(5, actividadInsert.getCupo());
        ps.setString(6, actividadInsert.getFecha_actividad());

        Constantes.printMssg(ps.toString());

        int rs = ps.executeUpdate();
        Constantes.printMssg("Lo que devuelve el executeUpdate() -> " + rs);

        if (rs == 1) {
            return Constantes.INSERT_ACTIVITIES_SUCCESS;
        } else {
            return Constantes.INSERT_ACTIVITIES_INVALID;
        }
    }

    public String updateActividad(ActividadModel updateActividad) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(UPDATE_ACTIVIDAD_BY_ID);
        ps.setString(1, updateActividad.getNombre_actividad());
        ps.setString(2, updateActividad.getDescripcion());
        ps.setBytes(3, updateActividad.getImagen());
        ps.setDouble(4, updateActividad.getPrecio());
        ps.setInt(5, updateActividad.getCupo());
        ps.setString(6, updateActividad.getFecha_actividad());
        ps.setInt(7, updateActividad.getId_actividad());

        Constantes.printMssg(ps.toString());

        int rs = ps.executeUpdate();
        Constantes.printMssg("Lo que devuelve el executeUpdate() -> " + rs);
        if (rs == 1) {
            return Constantes.UPDATE_ACTIVITIES_CORRECT;
        } else {
            return Constantes.UPDATE_ACTIVITIES_INVALID;
        }
    }

    public String deleteActividad(int id_actividad) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(DELETE_ACTIVITIE_BY_ID);
        ps.setInt(1, id_actividad);

        Constantes.printMssg(ps.toString());

        int rs = ps.executeUpdate();
        if (rs > 0) {
            Constantes.printMssg("result del executeUpdate -> " + rs);
            return Constantes.DELETE_ACTIVITY_SUCCESS;
        } else
            return Constantes.DELETE_ACTIVITIES_INVALID;
    }

    public boolean existsById(int idActividad) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(CHECK_NUM_ID);

        ps.setInt(1, idActividad);

        Constantes.printMssg(ps.toString());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            Constantes.printMssg("Número de filas encontradas con id_actividad: " + count);
            return count > 0;
        }
        return false;
    }
}
