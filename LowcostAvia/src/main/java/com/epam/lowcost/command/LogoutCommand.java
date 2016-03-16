package com.epam.lowcost.command;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Виктория on 18.02.2016.
 */
public class LogoutCommand extends ActionCommand {
    private static Logger logger = Logger.getLogger(LogoutCommand.class);

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = "/index.jsp";
        request.getSession().invalidate();
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);

    }
}
