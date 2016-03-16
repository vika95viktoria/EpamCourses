package com.epam.lowcost.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/airepam*"})
public class UnauthorizedFilter implements Filter {
    private String command;

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        command = req.getParameter("command");
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/index.jsp");
        if (role == null && command != null) {
            switch (command) {
                case "changeLanguage":
                case "cityTo":
                case "flights":
                case "register":
                case "routes":
                case "login": {
                    chain.doFilter(request, response);
                    return;
                }
                default:
                    dispatcher.forward(req, resp);
                    return;
            }
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
        command = null;
    }
}