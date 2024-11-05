package dao;

import model.UserModel;
import utils.Constantes;
import utils.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao extends ConnectionDB {
    public static final String USER_CHECK = "select id_usuario, nombre, email, rol, fecha_registro from usuarios where email = ? AND password = ?";
    //public static final String USER_CHECK = "select * from usuarios where email = ?";

    public UserModel checkLogin(String user, String password) throws SQLException {
        UserModel usuarioActual = new UserModel();
        PreparedStatement ps = this.connection.prepareStatement(USER_CHECK);
        ps.setString(1, user);
        ps.setString(2, password);

        Constantes.printMssg(ps.toString());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            usuarioActual.setId(rs.getInt("id_usuario"));
            usuarioActual.setNombre(rs.getString("nombre"));
            usuarioActual.setEmail(rs.getString("email"));
            usuarioActual.setRole(Role.valueOf(rs.getString("rol")));
            usuarioActual.setDate(rs.getString("fecha_registro"));
        }
        
        return usuarioActual;
    }
}
