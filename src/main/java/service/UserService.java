package service;

import dao.UserDao;
import model.UserModel;
import servlet.UserServlet;
import utils.Constantes;
import utils.Estadouser;
import utils.Role;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@SuppressWarnings("CommentedOutCode")
public class UserService {
    private static int getRequestCount = 0;
    private static int postRequestCount = 0;
    private final UserDao userDao;
    private UserModel user;
    private ArrayList<UserModel> usuarios;

    public UserService() {
        userDao = new UserDao();
        user = new UserModel();
        usuarios = new ArrayList<>();
    }

    public void handleGetRequest() {
        getRequestCount++;
        System.out.println("GET desde ActividadesService - Total de llamadas: " + getRequestCount);
    }

    public void handlePostRequest() {
        postRequestCount++;
        System.out.println("POST desde ActividadesService - Total de llamadas: " + postRequestCount);
    }

    public void getUsuarios(String id, HttpServletRequest req, HttpServletResponse resp, UserServlet userServlet) throws ServletException, IOException {

        if (id == null) {
            try {
                usuarios = this.obtenerTodosUsuarios();

                Constantes.printMssg(usuarios.toString());
                System.out.println(usuarios);

                req.setAttribute("usuarios", usuarios);
                req.getRequestDispatcher("/jsp/usuarios.jsp").forward(req, resp);
            } catch (SQLException | ClassNotFoundException e) {
                resp.getWriter().println("Error al obtener los usuarios: " + e.getMessage());
            }
        } else {
            try {
                user = this.obtenerUsuario(Integer.parseInt(id));
                //usuarios.add(user);
                //Constantes.printMssg(user.toString());
                req.setAttribute("usuarios", user);
                userServlet.getServletContext().getRequestDispatcher("/jsp/perfil.jsp").forward(req, resp);

            } catch (SQLException | ClassNotFoundException e) {
                resp.getWriter().println("Error al obtener los usuarios: " + e.getMessage());
            }
        }
    }

    // obtenemos todos los usuarios
    public ArrayList<UserModel> obtenerTodosUsuarios() throws SQLException, ClassNotFoundException {
        return userDao.getAllUsers();
    }

    public UserModel obtenerUsuario(int id) throws SQLException, ClassNotFoundException {
        return userDao.getUserByID(id);
    }

    public void actionBotton(HttpServletRequest req, HttpServletResponse resp, UserServlet userServlet) throws ServletException, IOException {
        String referer = req.getHeader("Referer");
        String msg = "";
        String action = req.getParameter("action");
System.out.println(req);
        switch (action) {
            case "add": {
                try {
                    msg = this.add(req);
                } catch (SQLException e) {
                    handleSQLException(e, resp);
                } catch (ClassNotFoundException e) {
                    resp.getWriter().println("Error de conexión: " + e.getMessage());
                } catch (Exception e) {
                    resp.getWriter().println("Error en el proceso de encriptación o en la función add: " + e.getMessage());
                }
                break;
            }

            case "update": {
                try {
                    msg = this.update(req, referer);
                } catch (SQLException e) {
                    handleSQLException(e, resp);
                } catch (ClassNotFoundException e) {
                    resp.getWriter().println("Error de conexión: " + e.getMessage());
                } catch (Exception e) {
                    resp.getWriter().println("Error en el proceso de encriptación o en la función update: " + e.getMessage());
                }
                break;
            }

            case "delete": {
                try {
                    msg = this.delete(req);
                } catch (SQLException e) {
                    handleSQLException(e, resp);
                } catch (ClassNotFoundException e) {
                    resp.getWriter().println("Error de conexión: " + e.getMessage());
                } catch (Exception e) {
                    resp.getWriter().println("Error en la función delete: " + e.getMessage());
                }
                break;
            }

            case "inactivar": {
                try {
                    msg = this.inactivar(req);
                } catch (SQLException e) {
                    handleSQLException(e, resp);
                } catch (ClassNotFoundException e) {
                    resp.getWriter().println("Error de conexión: " + e.getMessage());
                } catch (Exception e) {
                    resp.getWriter().println("Error en la función inactivar: " + e.getMessage());
                }
                break;
            }
        }

        Constantes.printMssg(msg);
        req.setAttribute("msg", msg);
        resp.sendRedirect(referer);
    }

    // manejar excepciones de SQL
    private void handleSQLException(SQLException e, HttpServletResponse resp) throws IOException {
        if (e.getMessage().contains("Duplicate entry")) {
            String msg = "El correo ya existe";
            System.out.println(msg);
            resp.getWriter().println(msg);
        } else {
            resp.getWriter().println("Error de base de datos: " + e.getMessage());
        }
    }


    public String add(HttpServletRequest req) throws Exception {

        String nombre = req.getParameter("nombre");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String rol = req.getParameter("rol");

        //encriptar contraseña
        SecretKey secretKey = EncriptarAesService.loadAESKey();
        String encryptedPassword = EncriptarAesService.encrypt(password, secretKey);


        user.setNombre(nombre);
        user.setEmail(email);
        user.setPassword(encryptedPassword);
        user.setRole(Role.valueOf(rol));
        user.setEstadouser(Estadouser.activo);

        // Configuración de sesión
        HttpSession session = req.getSession();
        session.setAttribute("usuario", user);

        return userDao.addUser(user);
    }

    public String update(HttpServletRequest req, String ref) throws Exception {

        UserModel usertemp = (UserModel) req.getSession().getAttribute("usuarios");

        String nombre = req.getParameter("nombre");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String rol = req.getParameter("rol");
        String id = req.getParameter("id");

        // Encriptar la contraseña antes de actualizar
        SecretKey secretKey = EncriptarAesService.loadAESKey();
        String encryptedPassword = EncriptarAesService.encrypt(password, secretKey);

        user.setId(Integer.parseInt(id));
        user.setNombre(nombre);
        user.setEmail(email);
        //user.setPassword(encryptedPassword);
        user.setPassword(usertemp.getPassword());  //Manera temporal para la contransña
        user.setRole(Role.valueOf(rol));
        user.setEstadouser(Estadouser.activo);

        System.out.println(user);

        if(ref.contains("admin")){
            req.getSession().setAttribute("usuario",user);
        }

        return userDao.update(user);
    }

    public String inactivar(HttpServletRequest req) throws SQLException, ClassNotFoundException {


        String nombre = req.getParameter("nombre");
        String email = req.getParameter("email");
//        String password = req.getParameter("password");
        String rol = req.getParameter("rol");
        String id = req.getParameter("id");

        user.setId(Integer.parseInt(id));
        user.setNombre(nombre);
        user.setEmail(email);
//        user.setPassword(password);
        user.setRole(Role.valueOf(rol));
        user.setEstadouser(Estadouser.inactivo);
        return userDao.update(user);
    }

    public String delete(HttpServletRequest req) throws SQLException, ClassNotFoundException {
        String id = req.getParameter("id");
        return userDao.Delete(Integer.parseInt(id));
    }
}