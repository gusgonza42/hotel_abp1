package servlet;

import model.ReservasActividadesModel;
import service.ReservasactividadesService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reservaactividad")
public class ReservasActvidades extends HttpServlet {
    private ReservasactividadesService reservasactividadesService;
    private ReservasActividadesModel lareserva;

    public ReservasActvidades() {
        reservasactividadesService = new ReservasactividadesService();
        lareserva = new ReservasActividadesModel();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        reservasactividadesService.handleGetRequest();
        this.reservasactividadesService.actionBottonGet(req.getParameter("id"), req, resp, this);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        reservasactividadesService.handlePostRequest();
        this.reservasactividadesService.actionBottonPost(req, resp, this);
    }


}
