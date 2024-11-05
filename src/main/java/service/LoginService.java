package service;

import dao.LoginDao;
import model.UserModel;
import servlet.LoginServlet;
import utils.Constantes;
import utils.Role;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginService {
    // Variable estática para contar las llamadas al método handleGetRequest
    private int getRequestCount = 0;
    // Variable estática para contar las llamadas al método handlePostRequest
    private int postRequestCount = 0;

    private LoginDao loginDao;
    private UserModel usuarioActual;

    public LoginService() {
        loginDao = new LoginDao();
        usuarioActual = new UserModel();
    }

    public void handleGetRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getRequestCount++;
        System.out.println("GET desde login - Total llamadas: " + getRequestCount);

        req.setAttribute("view", "/jsp/intro/login.jsp");
        req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    public void handlePostRequest() {
        postRequestCount++;
        System.out.println("POST desde LoginService - Total llamadas: " + postRequestCount);
    }

    //---------------------------- 💻Login -----------------------------
    public void login(HttpServletRequest req, HttpServletResponse resp, LoginServlet loginServlet) throws ServletException, IOException {
        String username = req.getParameter("usuario");
        String password = req.getParameter("password");

        // Ejecuta el login y obtiene el resultado
        String loginResult = this.checkLogin(username, password, req);

        // Redirige en función del resultado del login
        if (loginResult.equals(Constantes.USER_SUCCESSFULLY)) {
            Constantes.printMssg("Resultado del login: " + loginResult);
            req.setAttribute("view", "/jsp/intro/home.jsp");
        } else {
            req.setAttribute("ERROR", loginResult); // Mensaje de error específico de loginResult
            req.setAttribute("view", "/jsp/common/error.jsp");
        }

        // Despacha a index.jsp para cargar la vista correspondiente
        loginServlet.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }


    //---------------------------- 💻Login Check -----------------------------
    private String checkLogin(String username, String password, HttpServletRequest req) {
        try {
            // Cargar la clave AES para encriptar la contraseña
            SecretKey secretKey = EncriptarAesService.loadAESKey();
            String encryptedPassword = EncriptarAesService.encrypt(password, secretKey);

            // Conectar a la base de datos y verificar credenciales encriptadas
            this.loginDao.conectar();
            usuarioActual = this.loginDao.checkLogin(username, encryptedPassword);
            this.loginDao.desconectar();

            // Validar si se obtuvo un usuario válido
            if (usuarioActual != null && usuarioActual.getId() != 0) {
                HttpSession session = req.getSession();
                session.setAttribute("usuario", usuarioActual);
                return Constantes.USER_SUCCESSFULLY; // Login exitoso
            } else {
                return Constantes.ERROR_LOGIN_MESSAGE; // Usuario o contraseña incorrectos
            }

        } catch (SQLException e) {
            // Verifica si es un error de autenticación de base de datos
            if (e.getMessage().contains("Access denied")) {
                return "⚠️🔐 Error: credenciales de base de datos incorrectas.⚠️🔐";
            } else {
                return "⚠️Error de conexión con la base de datos.⚠️";
            }
        } catch (Exception e) {
            // Errores de encriptación u otros errores generales
            return "🗝️Error en el proceso de encriptación o de login: 🗝️" + e.getMessage();
        }
    }


}