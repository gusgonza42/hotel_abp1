package servlet;

import model.ReservasHabitacionesModel;
import service.ReservashabitacionesService;
import utils.Constantes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet("/habitacionReserva")
public class Reservashabitaciones extends HttpServlet {
    private ReservashabitacionesService reservashabitacionesService;


    public Reservashabitaciones() {
        reservashabitacionesService = new ReservashabitacionesService();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        reservashabitacionesService.handleGetRequest();
        this.reservashabitacionesService.getReservas(req.getParameter("id"), req, resp, this);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        reservashabitacionesService.handlePostRequest();
        this.reservashabitacionesService.actionBotton(req, resp, this);
    }
}

