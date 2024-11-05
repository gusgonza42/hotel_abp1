package service;

import dao.HabitacionesDao;
import model.HabitacionesModel;
import servlet.HabitacionesServlet;
import utils.Constantes;
import utils.EstadoHabitacion;
import utils.TipoHabitacion;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class HabitacionesService {
    // Variable estática para contar las llamadas al método handleGetRequest
    private int getRequestCount = 0;
    // Variable estática para contar las llamadas al método handlePostRequest
    private int postRequestCount = 0;
    // Almacenar las habitaciones en una variable de instancia
    private ArrayList<HabitacionesModel> habitaciones;
    private HabitacionesDao HabDao;
    private HabitacionesModel habitacion;


    public HabitacionesService() {
        habitaciones = new ArrayList<>();
        HabDao = new HabitacionesDao();
        habitacion = new HabitacionesModel();
    }

    public void handleGetRequest() {
        getRequestCount++;
        System.out.println("GET desde habitaciones - Total llamadas: " + getRequestCount);
    }

    public void handlePostRequest() {
        postRequestCount++;
        System.out.println("POST desde habitaciones - Total llamadas: " + getRequestCount);
    }

    public void getHabitaciones(HttpServletRequest req, HttpServletResponse resp, HabitacionesServlet habitacionesServlet) throws ServletException, IOException {

        String id = req.getParameter("id");

        if (id == null) {

            try {
                habitaciones = this.getAllHabitaciones();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println(habitaciones);
            Constantes.printMssg("Todas las habitaciones");

            // Pasamos el ArrayList como atributo a la
            req.setAttribute("view", "/jsp/habitaciones/habitaciones.jsp");
            req.setAttribute("habitaciones", habitaciones);
            habitacionesServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            //habitacionesServlet.getServletContext().getRequestDispatcher("/jsp/habitaciones.jsp").forward(req, resp);
        } else {

            try {
                habitacion = this.getHabitacioneById(req);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println(habitacion);
            Constantes.printMssg("Todas las habitaciones");
            // Pasamos el ArrayList como atributo a la solicitud
            req.setAttribute("habitacion", habitacion);
            habitacionesServlet.getServletContext().getRequestDispatcher("/jsp/habitaciones.jsp").forward(req, resp);
        }
    }

    public ArrayList<HabitacionesModel> getAllHabitaciones() throws SQLException, ClassNotFoundException {
        habitaciones = HabDao.getAllHabitaciones();
        return habitaciones;
    }

    public HabitacionesModel getHabitacioneById(HttpServletRequest req) throws SQLException, ClassNotFoundException {
        getRequestCount++;
        System.out.println("GET desde habitaciones - Total llamadas: " + getRequestCount);

        int id = Integer.parseInt(req.getParameter("id"));

        return HabDao.getHabitacionByID(id);
    }

    public void actionBotton(HttpServletRequest req, HttpServletResponse resp, HabitacionesServlet habitacionesServlet) throws ServletException, IOException {
        String referer = req.getHeader("Referer");
        String msg;
        String action = req.getParameter("action");

        switch (action) {
            case "update": {
                try {
                    msg = this.update(req);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Constantes.printMssg(msg);
                break;
            }
            case "delete": {

                int IDHabitacion = Integer.parseInt(req.getParameter("id"));
                try {
                    msg = this.delete(IDHabitacion);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Constantes.printMssg(msg);
                break;
            }
            case "add": {

                try {
                    msg = this.add(req);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(msg);

                break;
            }
        }
        resp.sendRedirect(referer);
       // habitacionesServlet.getServletContext().getRequestDispatcher("/jsp/habitaciones.jsp").forward(req, resp);
    }

    public String update(HttpServletRequest req) throws SQLException, ClassNotFoundException {
        // Obtenemos los parámetros del formulario
        int id = Integer.parseInt(req.getParameter("id"));
        String tipoHabitacion = req.getParameter("tipoHabitacion");
        double precio = Double.parseDouble(req.getParameter("precio"));
        String estadoHabitacion = req.getParameter("estadoHabitacion");

        HabitacionesModel habitacion = new HabitacionesModel(
                id,
                TipoHabitacion.valueOf(tipoHabitacion),
                null,
                precio,
                EstadoHabitacion.valueOf(estadoHabitacion));

        // req.setAttribute("habitaciones", habitaciones);
        return HabDao.update(habitacion);
    }

    public String add(HttpServletRequest req) throws SQLException, ClassNotFoundException {

        // Obtenemos los parámetros del formulario
        String tipoHabitacion = req.getParameter("tipoHabitacion");
        double precio = Double.parseDouble(req.getParameter("precio"));
        String estadoHabitacion = req.getParameter("estadoHabitacion");

        // Creamos un objeto de tipo HabitacionesModel
        HabitacionesModel habitacion = new HabitacionesModel();

        habitacion.setTipo_habitacion(TipoHabitacion.valueOf(tipoHabitacion));
        habitacion.setPrecio_habitacion(precio);
        habitacion.setEstado_habitacion(EstadoHabitacion.valueOf(estadoHabitacion));

        // Pasamos el ArrayList como atributo a la solicitud
        // req.setAttribute("habitaciones", habitaciones);

        return HabDao.add(habitacion);
    }

    public String delete(int id) throws SQLException, ClassNotFoundException {
        return HabDao.Delete(id);
    }


}