package servlet;

import model.HabitacionesModel;
import service.HabitacionesService;
import utils.Constantes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/habitaciones")
public class HabitacionesServlet extends HttpServlet {
    private HabitacionesService habitacionesService;

    public HabitacionesServlet() {
        habitacionesService = new HabitacionesService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        habitacionesService.handleGetRequest();
        this.habitacionesService.getHabitaciones(req, resp, this);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        habitacionesService.handlePostRequest();
        this.habitacionesService.actionBotton(req, resp, this);
    }
}