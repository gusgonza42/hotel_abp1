package service;

import service.ReservasactividadesService;
import service.ReservashabitacionesService;
import service.ActividadesService;
import servlet.Reservashabitaciones;
import servlet.ReservasActvidades;
import servlet.Reservas;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReservasService {
    // Variables estáticas para contar las llamadas a los métodos
    private static int getRequestCount = 0;
    private static int postRequestCount = 0;

    private ReservashabitacionesService habitacionService;
    private ReservasactividadesService reservaactividadService;
    private Reservashabitaciones habservlet;
    private ReservasActvidades actiservlet;
    private ActividadesService actividadesService;

    public ReservasService() {
        habitacionService = new ReservashabitacionesService();
        reservaactividadService = new ReservasactividadesService();
        habservlet = new Reservashabitaciones();
        actiservlet = new ReservasActvidades();
        actividadesService = new ActividadesService();
    }

    private void handleGetRequest() {
        getRequestCount++;
        System.out.println("GET desde ReservasService - Total de llamadas: " + getRequestCount);
    }

    private void handlePostRequest() {
        postRequestCount++;
        System.out.println("POST desde ReservasService - Total de llamadas: " + postRequestCount);
    }

    public void getHandler(HttpServletRequest req, HttpServletResponse resp, Reservas servlet) throws ServletException, IOException {
        handleGetRequest();
        try {
            habitacionService.getalllasReservas(req, resp, servlet);
            reservaactividadService.getalllasReservas(req, resp, servlet);
            actividadesService.getallactividades(req, resp);

            req.setAttribute("view", "/jsp/reservas/reservas.jsp");
            servlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            req.setAttribute("error", "Error al cargar las reservas");
            req.setAttribute("view", "/jsp/common/error.jsp");
            servlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    //--------------------------- POST ---------------------------------
    public void postHandler(HttpServletRequest req, HttpServletResponse resp, Reservas servlet) throws ServletException, IOException {
        handlePostRequest();
        String redirect = req.getParameter("reserva");

        if (redirect != null) {
            try {
                if ("habitacion".equals(redirect)) {
                    habitacionService.actionBotton(req, resp, habservlet);
                } else if ("actividad".equals(redirect)) {
                    reservaactividadService.actionBottonPost(req, resp, actiservlet);
                } else {
                    System.out.println("Unknown reservation type: " + redirect);
                }
                getHandler(req, resp, servlet);
            } catch (ServletException | IOException e) {

                req.setAttribute("error", "Error al cargar las reservas");
                req.setAttribute("view", "/jsp/common/error.jsp");
                servlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        } else {
            System.out.println("No reservation type provided in the request.");
        }
    }
}