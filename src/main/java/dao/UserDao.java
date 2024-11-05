package dao;

import model.UserModel;
import utils.Estadouser;
import utils.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao extends ConnectionDB {
    public static final String GETALLUSERS = "SELECT * FROM usuarios";
    public static final String GETUSERBYID = "SELECT * FROM usuarios WHERE id_usuario = ?";
    public static final String UPDATEUSER = "UPDATE usuarios SET nombre = ?,email =?,password =? ,rol =?, estado =? WHERE id_usuario = ?";
    public static final String DELETEUSER = "DELETE FROM usuarios WHERE id_usuario = ?";
    public static final String ADDEUSER = "INSERT INTO usuarios (nombre, email, password, rol,estado) VALUES (?,?,?,?,?)";

    public ArrayList<UserModel> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<UserModel> usuarios = new ArrayList<>();

        //conectar con la base de datos
        this.conectar();

        try (PreparedStatement ps = this.getConnection().prepareStatement(GETALLUSERS)) {
            ResultSet rs = ps.executeQuery();
            // ahora vamos a procesar los datos
            while (rs.next()) {
                UserModel usuario = new UserModel(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("rol")),
                        Estadouser.valueOf(rs.getString("estado")),
                        rs.getString("fecha_registro")
                );
                usuarios.add(usuario);
            }
        } finally {
            this.desconectar();
        }
        return usuarios;
    }

    public UserModel getUserByID(int id) throws SQLException, ClassNotFoundException {
        UserModel usuario = null;

        //conectar con la base de datos
        this.conectar();

        try (PreparedStatement ps = this.getConnection().prepareStatement(GETUSERBYID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            // ahora vamos a procesar los datos
            if (rs.next()) {
                usuario = new UserModel(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("rol").toLowerCase()),
                        Estadouser.valueOf(rs.getString("estado").toLowerCase()),
                        rs.getString("fecha_registro")
                );
            }
        } finally {
            this.desconectar();
        }
        return usuario;
    }

    public String addUser(UserModel user) throws SQLException, ClassNotFoundException {

        this.conectar();
        try (PreparedStatement ps = this.getConnection().prepareStatement(ADDEUSER)) {
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole().toString());
            ps.setString(5, user.getEstadouser().toString());

            int rs = ps.executeUpdate();

            if (rs > 0) {
                System.out.println(user);
                return "Usuario añadido";
            } else {
                return "Error: no puedo crear el usuario";
            }
        } finally {
            this.desconectar();
        }
    }

    public String update(UserModel user) throws SQLException, ClassNotFoundException {

        this.conectar();
        try (PreparedStatement ps = this.getConnection().prepareStatement(UPDATEUSER)) {
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole().toString());
            ps.setString(5, user.getEstadouser().toString());
            ps.setInt(6, user.getId());

            int rs = ps.executeUpdate();

            if (rs > 0) {
                System.out.println(user);
                System.out.println(rs);
                return "Usuario Actualizado";
            } else {
                return "Error: no puedo actualizar el usuario";
            }
        } finally {
            this.desconectar();
        }
    }

    public String Delete(int id) throws SQLException, ClassNotFoundException {

        this.conectar();
        try (PreparedStatement ps = this.getConnection().prepareStatement(DELETEUSER)) {

            ps.setInt(1, id);

            int rs = ps.executeUpdate();

            if (rs > 0) {
                System.out.println(id);
                return "Usuario Borrado";
            } else {
                return "Error: no puedo borrar el usuario";
            }
        } finally {
            this.desconectar();
        }
    }
}
