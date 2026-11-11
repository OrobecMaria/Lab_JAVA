package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/HelloAreaServlet")
public class AreaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AreaServlet() {
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
            writer.println("<title>Области</title></head><body>");
            writer.println("<h2>ПРИВЕТ AreaServlet</h2>");
            writer.println("<h3>Сущность: Область</h3>");
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