package dao;

import lombok.Getter;
import utils.Constantes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class ConnectionDB {
    // Método para obtener la conexión
    Connection connection;

    // Método para conectar a la base de datos
    public void conectar() throws SQLException, ClassNotFoundException {
        if (this.connection == null || this.connection.isClosed()) {
            try {
                Class.forName(Constantes.FOR_NAME_CLASS);
                this.connection = DriverManager.getConnection(Constantes.CONNECTION, Constantes.USER_NAME, Constantes.PASSWORD);
                System.out.println("\n☑️ Conexión a la base de datos establecida. ☑️\n");
            } catch (SQLException e) {
                System.err.println("⚠️Error de conexión a la base de datos: " + e.getMessage() + "⚠️");
                throw e; // Lanzar la excepción
            }
        }
    }

    // Método para desconectar de la base de datos
    public void desconectar() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
            System.out.println("\n 🔐Conexión a la base de datos cerrada.🔐\n");
        }
    }

}