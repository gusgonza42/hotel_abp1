package servlet;

import service.LoginService;
import utils.Constantes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//esto mostrará el login
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private LoginService loginService;

    public LoginServlet() {
        this.loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        loginService.handleGetRequest(req, resp);
        ;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        loginService.handlePostRequest();
        this.loginService.login(req, resp, this);
    }
}