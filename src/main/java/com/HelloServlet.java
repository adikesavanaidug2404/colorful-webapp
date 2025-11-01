package com.example.web;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        out.println("<html><body style='background:linear-gradient(to right,#ff416c,#ff4b2b);color:white;text-align:center;font-family:Poppins;'>");
        out.println("<h1>👋 Hello, " + name + "!</h1>");
        out.println("<p>Welcome to the colorful web application.</p>");
        out.println("<a href='index.html' style='color:white;text-decoration:none;'>Go Back</a>");
        out.println("</body></html>");
    }
}
