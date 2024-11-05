package service;

import dao.ReservaHabDao;
import model.ReservasHabitacionesModel;
import servlet.Reservas;
import servlet.Reservashabitaciones;
import utils.Constantes;
import utils.State;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservashabitacionesService {
    // Variable estática para contar las llamadas al método handleGetRequest
    private static int getRequestCount = 0;
    // Variable estática para contar las llamadas al método handlePostRequest
    private static int postRequestCount = 0;

    private ReservasHabitacionesModel lareserva;
    private ReservaHabDao reservadao;
    private ArrayList<ReservasHabitacionesModel> Lasreservas;

    public ReservashabitacionesService() {
        reservadao = new ReservaHabDao();
        Lasreservas = new ArrayList<>();
        lareserva = new ReservasHabitacionesModel();
    }

    public void handleGetRequest() {
        getRequestCount++;
        System.out.println("GET desde Reserva Habitaciones - Total de llamadas: " + getRequestCount);
    }

    public void handlePostRequest() {
        postRequestCount++;
        System.out.println("POST desde Reserva Habitaciones - Total de llamadas: " + postRequestCount);
    }

    public void getReservas(String id, HttpServletRequest req, HttpServletResponse resp, Reservashabitaciones reservashabitaciones) throws ServletException, IOException {
        if (req.getParameter(id) == null) {
            try {
                Lasreservas = this.getLasreservas(req);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Lasreservas + "\n ALL");

            req.setAttribute("reservas", Lasreservas);
            //  req.setAttribute("view", "/jsp/reservas/habitaciones/reserva_habitaciones.jsp");
            reservashabitaciones.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            try {
                lareserva = this.getLareserva(req);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println(lareserva + "\nBy ID");

            req.setAttribute("reservas", lareserva);
            req.setAttribute("view", "/jsp/reservas/habitaciones/reservas_habitaciones.jsp");
            reservashabitaciones.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    public ArrayList<ReservasHabitacionesModel> getLasreservas(HttpServletRequest req) throws SQLException, ClassNotFoundException {
        return reservadao.getAllReservas();
    }

    public ReservasHabitacionesModel getLareserva(HttpServletRequest req) throws SQLException, ClassNotFoundException {
        int id = Integer.parseInt(req.getParameter("id"));
        lareserva = reservadao.ReservabyID(id);
        return lareserva;
    }

    public void actionBotton(HttpServletRequest req, HttpServletResponse resp, Reservashabitaciones reservashabitaciones) throws ServletException, IOException {
        String action = req.getParameter("action");
        String msg = "";
        switch (action) {
            case "update": {
                try {
                    msg = this.update(req);
                } catch (SQLException | ClassNotFoundException e) {
                    req.setAttribute("ERROR-RESERVA-HABITACIONES", Constantes.ERROR_ACTIVIDAD_ID);
                    req.setAttribute("view", "/jsp/common/error.jsp");
                    reservashabitaciones.getServletContext().getRequestDispatcher("./jsp/index.jsp").forward(req, resp);

                }
                Constantes.printMssg(msg);
                break;
            }
            case "cancelado":
            case "completado": {
                try {
                    msg = this.updateState(req);
                } catch (SQLException | ClassNotFoundException e) {
                    req.setAttribute("ERROR-RESERVA-HABITACIONES", Constantes.ERROR_ACTIVIDAD_ID);
                    req.setAttribute("view", "/jsp/common/error.jsp");
                    reservashabitaciones.getServletContext().getRequestDispatcher("./jsp/index.jsp").forward(req, resp);
                }
                Constantes.printMssg(msg);
                break;
            }
            case "add": {
                try {
                    msg = this.add(req);
                    resp.sendRedirect(req.getContextPath() + "/habitaciones");
                } catch (SQLException | ClassNotFoundException e) {
                    req.setAttribute("ERROR-RESERVA-HABITACIONES", Constantes.ERROR_ACTIVIDAD_ID);
                    req.setAttribute("view", "/jsp/common/error.jsp");
                    reservashabitaciones.getServletContext().getRequestDispatcher("./jsp/index.jsp").forward(req, resp);
                }
                Constantes.printMssg(msg);
                break;
            }
        }
        /*req.setAttribute("reserva-habitaciones", msg);
        reservashabitaciones.getServletContext().getRequestDispatcher("/jsp/actividades.jsp").forward(req, resp);*/
    }


    public String add(HttpServletRequest req) throws SQLException, ClassNotFoundException {

        String IdUsuario = req.getParameter("IdUsuario");
        String IdHabitacion = req.getParameter("IdHabitacion");
        String FechaEntrada = req.getParameter("FechaEntrada");
        String FechaSalida = req.getParameter("FechaSalida");

        lareserva.setIdUsuario(Integer.parseInt(IdUsuario));
        lareserva.setIdHabitacion(Integer.parseInt(IdHabitacion));
        lareserva.setFechaEntrada(FechaEntrada);
        lareserva.setFechaSalida(FechaSalida);
        lareserva.setEstado(State.reservado);

        return reservadao.add(lareserva);
    }

    public String update(HttpServletRequest req) throws SQLException, ClassNotFoundException {

        String IdReserva = req.getParameter("IdReserva");
        String IdUsuario = req.getParameter("IdUsuario");
        String IdHabitacion = req.getParameter("IdHabitacion");
        String FechaEntrada = req.getParameter("FechaEntrada");
        String FechaSalida = req.getParameter("FechaSalida");
        String estado = req.getParameter("estado");

        lareserva.setIdReserva(Integer.parseInt(IdReserva));
        lareserva.setIdUsuario(Integer.parseInt(IdUsuario));
        lareserva.setIdHabitacion(Integer.parseInt(IdHabitacion));
        lareserva.setFechaEntrada(FechaEntrada);
        lareserva.setFechaSalida(FechaSalida);
        lareserva.setEstado(State.valueOf(estado));

        return reservadao.update(lareserva);
    }

    public String updateState(HttpServletRequest req) throws SQLException, ClassNotFoundException {
        String IdReserva = req.getParameter("IdReserva");
        String IdUsuario = req.getParameter("IdUsuario");
        String IdHabitacion = req.getParameter("IdHabitacion");
        String FechaEntrada = req.getParameter("FechaEntrada");
        String FechaSalida = req.getParameter("FechaSalida");
        String action = req.getParameter("action");

        lareserva.setIdReserva(Integer.parseInt(IdReserva));
        lareserva.setIdUsuario(Integer.parseInt(IdUsuario));
        lareserva.setIdHabitacion(Integer.parseInt(IdHabitacion));
        lareserva.setFechaEntrada(FechaEntrada);
        lareserva.setFechaSalida(FechaSalida);
        lareserva.setEstado(State.valueOf(action));

        return reservadao.update(lareserva);
    }

    public void getalllasReservas(HttpServletRequest req, HttpServletResponse resp, Reservas reservas) {
        try {
            Lasreservas = this.getLasreservas(req);
            req.setAttribute("reservashab", Lasreservas);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
