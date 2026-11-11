package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/HelloDistrictServlet")
public class DistrictServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DistrictServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        try {
            writer.println("<!DOCTYPE html>");
            writer.println("<html lang='ru'><head><meta charset='UTF-8'>");
            writer.println("<title>Районы</title></head><body>");
            writer.println("<h2>ПРИВЕТ DistrictServlet</h2>");
            writer.println("<h3>Сущность: Район</h3>");
            writer.println("<ul>");
            writer.println("<li>Название</li>");
            writer.println("<li>Площадь</li>");
            writer.println("<li>Административный центр</li>");
            writer.println("<li>Глава</li>");
            writer.println("</ul>");
            writer.println("</body></html>");
        } finally {
            writer.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}