package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false); // Obtener la sesión sin crear una nueva

        // Verifica si hay un usuario en la sesión
        if (session != null && session.getAttribute("usuario") != null) {
            // Si el usuario está autenticado, carga la vista de home
            req.setAttribute("view", "/jsp/intro/home.jsp");
        } else {
            // Si no hay un usuario en la sesión, redirige al login
            req.setAttribute("view", "/jsp/intro/login.jsp");
            req.setAttribute("ERROR", "Debe iniciar sesión para acceder a esta página.");
        }
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}