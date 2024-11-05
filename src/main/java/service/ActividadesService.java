package service;

import dao.ActividadesDao;
import model.ActividadModel;
import model.UserModel;
import servlet.ActividadesServlet;
import utils.Constantes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActividadesService {
    // Variable estática para contar las llamadas al método handleGetRequest
    private static int getRequestCount = 0;
    // Variable estática para contar las llamadas al método handlePostRequest
    private static int postRequestCount = 0;
    private ActividadesDao actividadesDao;
    private UserModel user;

    public ActividadesService() {
        this.actividadesDao = new ActividadesDao();
        this.user = new UserModel();
    }

    public void handleGetRequest() {
        getRequestCount++;
        System.out.println("GET desde ActividadesService - Total de llamadas: " + getRequestCount);
    }

    public void handlePostRequest() {
        postRequestCount++;
        System.out.println("POST desde ActividadesService - Total de llamadas: " + postRequestCount);
    }

    public void getActividades(String id, HttpServletRequest req, HttpServletResponse resp, ActividadesServlet actividadesServlet) throws ServletException, IOException {

        try {
            System.out.println(id);
            if (id == null) {

               getallactividades(req,resp);

                req.setAttribute("view", "/jsp/actividades/actividades.jsp");


                actividadesServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                //actividadesServlet.getServletContext().getRequestDispatcher("/jsp/actividades.jsp").forward(req, resp);
            } else {
                this.actividadesDao.conectar();
                ActividadModel actividadById = this.actividadesDao.getByIdActividades(Integer.parseInt(id));
                this.actividadesDao.desconectar();

                if (actividadById.getId_actividad() == 0) {
                    Constantes.printMssg(Constantes.ERROR_ACTIVIDAD_ID);
                    req.setAttribute("ERROR", Constantes.ERROR_ACTIVIDAD_ID);

                    req.setAttribute("view", "/jsp/common/error.jsp");

                    actividadesServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                } else {
                    Constantes.printMssg(actividadById.toString());
                    req.setAttribute("view", "/jsp/actividades/actividades.jsp");
                    req.setAttribute("actividades", actividadById);
                    actividadesServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                }
            }
        } catch (Exception e) {
            req.setAttribute("ERROR", e.getMessage());
            req.setAttribute("view", "/jsp/common/error.jsp");
            actividadesServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            Constantes.printMssg(Constantes.INSERT_ACTIVITIES_INVALID);
        }

    }

    public void getallactividades(HttpServletRequest req, HttpServletResponse resp){
        ArrayList<ActividadModel> actividades;

        try {
            this.actividadesDao.conectar();
            actividades = this.actividadesDao.getAllActividades();
            this.actividadesDao.desconectar();
            Constantes.printMssg(actividades.toString());
            req.setAttribute("actividades", actividades);
            req.getSession().setAttribute("actividades", actividades);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void actionBotton(HttpServletRequest req, HttpServletResponse resp, ActividadesServlet actividadesServlet) throws ServletException, IOException {

        String action = req.getParameter("action");
        String msg;
        switch (action) {
            case "add": {
                msg = this.insertActividades(req, resp, actividadesServlet);
                Constantes.printMssg(msg);
                break;
            }
            case "update": {
                msg = this.updateActividades(req, resp, actividadesServlet);
                Constantes.printMssg(msg);
                break;
            }
            case "delete": {
                msg = this.deleteActividades(req, resp, actividadesServlet);
                Constantes.printMssg(msg);
                break;
            }
        }

    }

    public String insertActividades(HttpServletRequest req, HttpServletResponse resp, ActividadesServlet actividadesServlet) throws ServletException, IOException {
        ActividadModel actividadInsert = new ActividadModel();
        String referer = req.getHeader("Referer");
        String msgInsertActividad;
        try {
            this.actividadesDao.conectar();
            actividadInsert.setNombre_actividad(req.getParameter("nombre_actividad"));
            actividadInsert.setDescripcion(req.getParameter("descripcion"));
          //  actividadInsert.setImagen(req.getParameter("imagen").getBytes());
            actividadInsert.setPrecio(Double.parseDouble(req.getParameter("precio")));
            actividadInsert.setCupo(Integer.parseInt(req.getParameter("cupo")));
            actividadInsert.setFecha_actividad(req.getParameter("fecha_actividad"));

            //mostramos la actividad insertada desde POSTMAN
            Constantes.printMssg(actividadInsert.toString());

            msgInsertActividad = this.actividadesDao.insertActividades(actividadInsert);

            this.actividadesDao.desconectar();
            //redirigimos a la vista de actividades enviando el mensaje de que la actividad ha sido agregada
            req.setAttribute("actividades", msgInsertActividad);

        } catch (Exception e) {
            req.setAttribute("ERROR-DDBB-CONNECTION-ACTIVIDADES", e.getMessage());
            req.setAttribute("view", "/jsp/common/error.jsp");
            actividadesServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            msgInsertActividad = Constantes.CONNECTION_DDBB_INVALID;
        }
        resp.sendRedirect(referer);
        return msgInsertActividad;
    }

    public String updateActividades(HttpServletRequest req, HttpServletResponse resp, ActividadesServlet actividadesServlet) throws ServletException, IOException {
        String msgUpdateActividades;
        String referer = req.getHeader("Referer");
        ActividadModel actividadUpdate = new ActividadModel();

        try {
            this.actividadesDao.conectar();

            int idActividad = Integer.parseInt(req.getParameter("id_actividad"));

            // Comprobar si el ID existe antes de actualizar
            boolean idExists = this.actividadesDao.existsById(idActividad);
            if (!idExists) {
                msgUpdateActividades = Constantes.NOT_EXITS_IDS;
                req.setAttribute("ERROR-ID-NOT-FOUND", msgUpdateActividades);
                req.setAttribute("view", "/jsp/common/error.jsp");
                actividadesServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                return msgUpdateActividades;
            }

            // Si el ID existe, seguimos con el update
            actividadUpdate.setId_actividad(idActividad);
            actividadUpdate.setNombre_actividad(req.getParameter("nombre_actividad"));
            actividadUpdate.setImagen(req.getParameter("imagen").getBytes());
            actividadUpdate.setPrecio(Double.parseDouble(req.getParameter("precio")));
            actividadUpdate.setCupo(Integer.parseInt(req.getParameter("cupo")));
            actividadUpdate.setFecha_actividad(req.getParameter("fecha_actividad"));

            msgUpdateActividades = this.actividadesDao.updateActividad(actividadUpdate);

            this.actividadesDao.desconectar();

            req.setAttribute("actividades", msgUpdateActividades);

        } catch (Exception e) {
            req.setAttribute("ERROR-DDBB-CONNECTION-ACTIVIDADES", e.getMessage());
            req.setAttribute("view", "/jsp/common/error.jsp");
            actividadesServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            msgUpdateActividades = Constantes.CONNECTION_DDBB_INVALID;
        }
        resp.sendRedirect(referer);
        return msgUpdateActividades;
    }


    public String deleteActividades(HttpServletRequest req, HttpServletResponse resp, ActividadesServlet actividadesServlet) throws ServletException, IOException {
        String msgDeleteActividades;
        String referer = req.getHeader("Referer");
        try {
            this.actividadesDao.conectar();

            int idActividad = Integer.parseInt(req.getParameter("id_actividad"));

            // Comprobar si el ID existe antes de eliminar
            boolean idExists = this.actividadesDao.existsById(idActividad);
            if (!idExists) {
                msgDeleteActividades = Constantes.NOT_EXITS_IDS;
                req.setAttribute("ERROR-ID-NOT-FOUND", msgDeleteActividades);
                req.setAttribute("view", "/jsp/common/error.jsp");
                actividadesServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                return msgDeleteActividades;
            }

            msgDeleteActividades = this.actividadesDao.deleteActividad(idActividad);

            this.actividadesDao.desconectar();

            req.setAttribute("actividades", msgDeleteActividades);

        } catch (Exception e) {
            req.setAttribute("ERROR-DDBB-CONNECTION-ACTIVIDADES", e.getMessage());
            req.setAttribute("view", "/jsp/common/error.jsp");
            actividadesServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            msgDeleteActividades = Constantes.CONNECTION_DDBB_INVALID;
        }
        resp.sendRedirect(referer);
        return msgDeleteActividades;
    }
}