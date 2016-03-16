package com.epam.lowcost.command;

import com.epam.lowcost.domain.User;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.security.PasswordHash;
import com.epam.lowcost.service.UserService;
import com.epam.lowcost.util.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Виктория on 18.02.2016.
 */
public class LoginCommand extends ActionCommand {
    private static final String PARAM_NAME_USERNAME = "username";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException, IOException {
        String page;
        String username = request.getParameter(PARAM_NAME_USERNAME);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        UserService userService = UserService.getInstance();
        if (Validator.validateLoginForm(username, password)) {

            User user = userService.findByUsernamePassword(username, PasswordHash.getSecurePassword(password));
            HttpSession session = request.getSession();
            if (user != null) {
                session.setAttribute("currentUser", user.getUsername());
                session.setAttribute("userId", user.getId());
                if (user.isAdmin()) {
                    session.setAttribute("role", "admin");
                } else {
                    session.setAttribute("role", "user");
                }
            } else {
                request.setAttribute("errorLoginPassMessage", "Incorrect username or password");
            }
            page = "/index.jsp";
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);


        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        }
    }
}

