package servlet;

import model.UserModel;
import service.AdminService;
import utils.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private AdminService adminService;

    public AdminServlet() {
        adminService = new AdminService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        adminService.handleGetRequest();
        adminService.setvals(req,resp);
        HttpSession session = req.getSession(false); // Obtener la sesión sin crear una nueva

        // Verifica si hay un usuario en la sesión
        UserModel usuarioActual = (UserModel) (session != null ? session.getAttribute("usuario") : null);

        if (usuarioActual == null) {
            // Si no hay usuario en la sesión, redirige al login
            resp.sendRedirect("login");
        } else if (usuarioActual.getRole() != Role.admin) {
            // Si el usuario no es admin, redirige a la página de inicio con mensaje de error
            req.setAttribute("ERROR", "Acceso denegado. Solo los administradores pueden acceder a esta página.");
            req.setAttribute("view", "/jsp/intro/home.jsp");
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            // Si el usuario es admin, carga la vista de administración
            req.setAttribute("view", "/jsp/admin/admin.jsp");
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        adminService.handlePostRequest();

        // Ejemplo de procesamiento para solicitudes POST en la administración
        int userId = Integer.parseInt(req.getParameter("idUser"));
        adminService.printAdminId(userId);

        // Redirigir de vuelta a la página de administración después del procesamiento
        resp.sendRedirect("admin");
    }
}