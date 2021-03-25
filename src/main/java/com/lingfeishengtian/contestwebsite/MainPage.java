package com.lingfeishengtian.contestwebsite;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("")
public class MainPage extends HttpServlet {
    public static String competition_name;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("name", competition_name);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
