package service;

import dao.ReservaActDao;
import model.ReservasActividadesModel;
import servlet.Reservas;
import servlet.ReservasActvidades;
import utils.Constantes;
import utils.State;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservasactividadesService {
    // Variable estática para contar las llamadas al método handleGetRequest
    private static int getRequestCount = 0;
    // Variable estática para contar las llamadas al método handlePostRequest
    private static int postRequestCount = 0;

    ReservasActividadesModel lareserva;
    ReservaActDao reservadao;
    private ArrayList<ReservasActividadesModel> reserva;


    public ReservasactividadesService() {
        lareserva = new ReservasActividadesModel();
        reservadao = new ReservaActDao();
        reserva = new ArrayList<>();

    }

    public void handleGetRequest() {
        getRequestCount++;
        System.out.println("GET desde ActividadesService - Total de llamadas: " + getRequestCount);
    }

    public void handlePostRequest() {
        postRequestCount++;
        System.out.println("POST desde ActividadesService - Total de llamadas: " + postRequestCount);
    }

    public void actionBottonGet(String id, HttpServletRequest req, HttpServletResponse resp, ReservasActvidades reservasActvidades) throws ServletException, IOException {
        if (req.getParameter(id) == null) {
            try {
                reserva = this.LasReservas();
                req.setAttribute("view","/jsp/reserva_actividades.jsp");
                req.setAttribute("reserva", reserva);

               // reservasActvidades.getServletContext().getRequestDispatcher("/jsp/reserva_actividades.jsp").forward(req, resp);
                reservasActvidades.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            } catch (SQLException | ClassNotFoundException e) {
                req.setAttribute("ERROR-RESERVA-ACTIVIDAES", e.getMessage());
                //System.out.println(e.getMessage());
                reservasActvidades.getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
            }
            System.out.println(reserva + "\n ALL");


        } else {
            try {
                lareserva = this.getLareservaConID(req);
            } catch (SQLException | ClassNotFoundException e) {
                req.setAttribute("ERROR-RESERVA-ACTIVIDAES", e.getMessage());
                reservasActvidades.getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
            }
            System.out.println(lareserva + "\nBy ID");

            req.setAttribute("reserva", lareserva);
            reservasActvidades.getServletContext().getRequestDispatcher("/jsp/reserva_actividades.jsp").forward(req, resp);
        }
    }

    public ReservasActividadesModel getLareservaConID(HttpServletRequest req) throws SQLException, ClassNotFoundException {

        int id = Integer.parseInt(req.getParameter("id"));
        return reservadao.ReservabyID(id);
    }

    public ArrayList<ReservasActividadesModel> LasReservas() throws SQLException, ClassNotFoundException {
        return reservadao.getAllReservas();
    }

    public void actionBottonPost(HttpServletRequest req, HttpServletResponse resp, ReservasActvidades reservasActvidades) throws ServletException, IOException {
        String action = req.getParameter("action");
        String msg = "";
        System.out.println(action);
        switch (action) {
            case "update": {
                try {
                    msg = this.update(req);
                } catch (SQLException | ClassNotFoundException e) {
                    req.setAttribute("ERROR-RESERVA-ACTIVIDAES", e.getMessage());
                    reservasActvidades.getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
                }
                Constantes.printMssg(msg);
                break;
            }
            case "cancelado":
            case "completado": {
                try {
                    msg = this.updateState(req);
                } catch (SQLException | ClassNotFoundException e) {
                    req.setAttribute("ERROR-RESERVA-ACTIVIDAES", e.getMessage());
                    reservasActvidades.getServletContext().getRequestDispatcher("/jsp/common/error.jsp").forward(req, resp);
                }
                Constantes.printMssg(msg);
                break;
            }
            case "add": {
                try {
                    msg = this.add(req);
                    resp.sendRedirect(req.getContextPath() + "/actividades");
                    Constantes.printMssg(msg);
                } catch (SQLException | ClassNotFoundException e) {
                    req.setAttribute("ERROR-RESERVA-ACTIVIDAES", e.getMessage());
                    reservasActvidades.getServletContext().getRequestDispatcher("/jsp/common/error.jsp").forward(req, resp);
                }
                break;
            }
        }
       /* req.setAttribute("reservaActividades", msg);
        reservasActvidades.getServletContext().getRequestDispatcher("/jsp/reserva_actividades.jsp").forward(req, resp);*/
    }


    public String add(HttpServletRequest req) throws SQLException, ClassNotFoundException {
        String IdUsuario = req.getParameter("IdUsuario");
        String IdActividad = req.getParameter("IdActividad");

        lareserva.setIdUsuario(Integer.parseInt(IdUsuario));
        lareserva.setIdActividad(Integer.parseInt(IdActividad));
        lareserva.setEstado(State.reservado);

        return reservadao.add(lareserva);
    }

    public String update(HttpServletRequest req) throws SQLException, ClassNotFoundException {

        String IdUsuario = req.getParameter("IdUsuario");
        String IdActividad = req.getParameter("IdActividad");
        String IdReserva = req.getParameter("IdReserva");
        String estado = req.getParameter("estado");

        lareserva.setIdUsuario(Integer.parseInt(IdUsuario));
        lareserva.setIdActividad(Integer.parseInt(IdActividad));
        lareserva.setIdReservasActividad(Integer.parseInt(IdReserva));

        return reservadao.update(lareserva);
    }

    public String updateState(HttpServletRequest req) throws SQLException, ClassNotFoundException {

        String IdReserva = req.getParameter("IdReserva");
        String IdUsuario = req.getParameter("IdUsuario");
        String IdActividad = req.getParameter("IdActividad");
        String estado = req.getParameter("action");

        lareserva.setIdReservasActividad(Integer.parseInt(IdReserva));
        lareserva.setIdUsuario(Integer.parseInt(IdUsuario));
        lareserva.setIdActividad(Integer.parseInt(IdActividad));
        lareserva.setEstado(State.valueOf(estado));
        return reservadao.update(lareserva);
    }

    public void getalllasReservas(HttpServletRequest req, HttpServletResponse resp, Reservas reservas) {
        try {
            reserva = this.LasReservas();
            req.setAttribute("reservasact", reserva);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }





}
