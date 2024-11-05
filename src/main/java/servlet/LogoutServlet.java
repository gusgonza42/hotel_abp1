package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtén la sesión actual sin crear una nueva
        HttpSession session = req.getSession(false);

        // Si la sesión existe, la invalida para cerrar la sesión del usuario
        if (session != null) {
            session.invalidate();
            System.out.println("Sesión cerrada correctamente.");
        }

        // Redirige al usuario a la página de login
        req.setAttribute("view","/jsp/intro/login.jsp");
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}