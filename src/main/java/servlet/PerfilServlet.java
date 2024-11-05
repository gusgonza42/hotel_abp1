package servlet;

import model.UserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/perfil")
public class PerfilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserModel usuarioActual = (UserModel) session.getAttribute("usuario");

        // Verificar si hay un usuario en sesión
        if (usuarioActual == null) {
            // Si no hay usuario en sesión, redirige al login
            resp.sendRedirect("login");
            return;
        }

        // Si el usuario está en sesión, se configura la vista del perfil
        req.setAttribute("usuario", usuarioActual);
        req.setAttribute("view", "/jsp/usuario/perfil.jsp");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // No se implementa
        super.doPost(req, resp);
    }
}