package com.epam.lowcost.command;

import com.epam.lowcost.domain.Ticket;
import com.epam.lowcost.domain.User;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.resource.ConfigurationManager;
import com.epam.lowcost.service.TicketService;
import com.epam.lowcost.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.lowcost.util.CommandConstants.*;

/**
 * Created by Виктория on 03.03.2016.
 */
public class ManageCommand extends ActionCommand {
    /**
     * Get tickets for user, redirect to cabinet page
     *
     * @param request
     * @param response
     * @throws ServiceException
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException, IOException {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadProperties(PATH_PROPERTIES_FILE);
        String page = configurationManager.getProperty(CABINET_PATH);
        TicketService ticketService = TicketService.getInstance();
        UserService userService = UserService.getInstance();
        HttpSession session = request.getSession();
        Long userId = Long.parseLong(session.getAttribute(ATTRIBUTE_NAME_USER_ID).toString());
        User user = userService.findUserById(userId);
        List<Ticket> tickets = ticketService.getAllTicketsForUser(userId);
        request.setAttribute(ATTRIBUTE_NAME_TICKETS, tickets);
        request.setAttribute(ATTRIBUTE_NAME_USER_CURRENT, user);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);

    }
}
