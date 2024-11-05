package servlet;


import service.RegistroService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//esto hace de view
@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    private RegistroService registroService;

    public RegistroServlet() {
        registroService = new RegistroService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        registroService.handleGetRequest();
        registroService.obtenerUsuariosRegister(req, resp, this);
        registroService.showPageRegister(req, resp, this);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        registroService.handlePostRequest();
        registroService.registrarUsuario(req, resp, this);
    }
}