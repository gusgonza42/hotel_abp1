package service;

import model.UserModel;
import utils.EstadoHabitacion;
import utils.Role;
import utils.TipoHabitacion;
import service.UserService;
import servlet.UserServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminService {
    // Variable estática para contar las llamadas al método handleGetRequest
    private int getRequestCout = 0;
    // Variable estática para contar las llamadas al método handlePostRequest
    private int postRequestCout = 0;
    UserService userService;
    private ArrayList<UserModel> usuarios;
    public AdminService() {
        userService = new UserService();
        usuarios = new ArrayList<>();
    }

    public void handleGetRequest() {
        getRequestCout++;
        System.out.println("Get desde admin - Total llamadas: " + getRequestCout);
    }

    public void handlePostRequest() {
        // Incrementa el contador de solicitudes POST
        postRequestCout++;
        // Muestra el total de solicitudes POST
        System.out.println("Post desde admin - Total llamadas: " + postRequestCout);
    }

    public void printAdminId(int idUser) {
        // Muestra el ID de administrador recibido
        System.out.println(idUser);
    }

    public void setvals(HttpServletRequest req, HttpServletResponse resp){
        req.setAttribute("tipo_habitacion", TipoHabitacion.values());
        req.setAttribute("roles", Role.values());
        req.setAttribute("Estado", EstadoHabitacion.values());

        try {
            usuarios = userService.obtenerTodosUsuarios();
            req.setAttribute("usuarios",usuarios);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}