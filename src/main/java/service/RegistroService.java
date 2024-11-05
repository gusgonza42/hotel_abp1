package service;

import model.UserModel;
import servlet.RegistroServlet;
import utils.Constantes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegistroService {
    private int getRequestCount = 0;
    private int postRequestCount = 0;
    private UserService userService;

    public RegistroService() {
        userService = new UserService();
    }

    public void handleGetRequest() {
        getRequestCount++;
        System.out.println("GET desde Register - Total llamadas: " + getRequestCount);
    }

    public void handlePostRequest() {
        postRequestCount++;
        System.out.println("POST desde Register - Total llamadas: " + postRequestCount);
    }

    public void showPageRegister(HttpServletRequest req, HttpServletResponse resp, RegistroServlet registroServlet) throws ServletException, IOException {
        req.setAttribute("view", "/jsp/intro/registro.jsp");
        registroServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    public void obtenerUsuariosRegister(HttpServletRequest req, HttpServletResponse resp, RegistroServlet registroServlet) throws ServletException, IOException {
        try {
            ArrayList<UserModel> users = userService.obtenerTodosUsuarios();
            req.setAttribute("view", "/jsp/intro/home.jsp");
            req.setAttribute("usuarios", users);
        } catch (SQLException e) {
            manejarErrorBD(e, req, resp);

        } catch (ClassNotFoundException e) {
            req.setAttribute("ERROR", "Error al cargar el controlador de la base de datos: " + e.getMessage());
            req.setAttribute("view", "/jsp/common/error.jsp");
            registroServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("ERROR", "Error general en el proceso de obtención de usuarios: " + e.getMessage());
            req.setAttribute("view", "/jsp/common/error.jsp");
            registroServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    // Método para registrar un nuevo usuario
    public void registrarUsuario(HttpServletRequest req, HttpServletResponse resp, RegistroServlet registroServlet) throws ServletException, IOException {
        try {
            Constantes.printMssg("Nombre: " + req.getParameter("nombre") + " Email: " + req.getParameter("email") + " Password: " + req.getParameter("password") + "Rol: " + req.getParameter("rol"));

            // Registrar usuario mediante UserService
            userService.add(req);
            // Obtener lista actualizada de usuarios
            ArrayList<UserModel> updatedUsers = userService.obtenerTodosUsuarios();
            req.setAttribute("view", "/jsp/intro/home.jsp");
            req.setAttribute("usuarios", updatedUsers);
            registroServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);

        } catch (SQLException e) {
            manejarErrorBD(e, req, resp);

        } catch (ClassNotFoundException e) {
            req.setAttribute("ERROR", "⚠️ Error al cargar el controlador de la base de datos: " + e.getMessage());
            req.setAttribute("view", "/jsp/common/error.jsp");
            registroServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("ERROR", "⚠️ Error general en el registro: " + e.getMessage());
            req.setAttribute("view", "/jsp/common/error.jsp");
            registroServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    // Manejo de errores específicos de base de datos
    private void manejarErrorBD(SQLException e, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (e.getMessage().contains("Access denied")) {
            req.setAttribute("ERROR", "⚠️ Error de autenticación en la base de datos: verifique las credenciales.");
        } else {
            req.setAttribute("ERROR", "⚠️ Error de base de datos: " + e.getMessage());
        }
        req.setAttribute("view", "/jsp/common/error.jsp");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

}