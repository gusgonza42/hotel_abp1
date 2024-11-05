package servlet;

import service.ReservasService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/reservas")
public class Reservas extends HttpServlet {

    ReservasService service;

    public Reservas() {

        service = new ReservasService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
service.getHandler(req,resp,this);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            service.postHandler(req,resp,this);

    }
}

