package servlet;

import service.ActividadesService;
import utils.Constantes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/actividades")
public class ActividadesServlet extends HttpServlet {

    private ActividadesService actividadesService;

    public ActividadesServlet() {
        this.actividadesService = new ActividadesService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        actividadesService.handleGetRequest();
        req.getCharacterEncoding();
        this.actividadesService.getActividades(req.getParameter("id"), req, resp, this);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        actividadesService.handlePostRequest();
        req.setCharacterEncoding("UTF-8");
        this.actividadesService.actionBotton(req, resp, this);
    }
}