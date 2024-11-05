package servlet;

import service.UserService;
import utils.Constantes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/usuarios")
public class UserServlet extends HttpServlet {
    private UserService userService;


    public UserServlet() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.handleGetRequest();
        this.userService.getUsuarios(req.getParameter("id"), req, resp, this);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       userService.handlePostRequest();
       this.userService.actionBotton(req, resp, this);
    }
}
