package com.epam.lowcost.filter;

import com.epam.lowcost.resource.ConfigurationManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.lowcost.util.CommandConstants.*;
import static com.epam.lowcost.util.Commands.*;

@WebFilter(urlPatterns = {"/airepam*"})
public class UnauthorizedFilter implements Filter {
    private String command;

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        command = req.getParameter(PARAM_NAME_COMMAND);
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute(ATTRIBUTE_NAME_ROLE);
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadProperties(PATH_PROPERTIES_FILE);
        String page = configurationManager.getProperty(INDEX_PATH);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        if (role == null && command != null) {
            switch (command) {
                case CHANGE_LANGUAGE_COMMAND:
                case CITY_TO_COMMAND:
                case FLIGHTS_COMMAND:
                case REGISTER_COMMAND:
                case ROUTES_COMMAND:
                case LOGIN_COMMAND: {
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