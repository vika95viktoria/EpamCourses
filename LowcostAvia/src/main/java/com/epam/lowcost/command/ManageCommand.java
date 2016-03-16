package com.epam.lowcost.command;

import com.epam.lowcost.domain.Ticket;
import com.epam.lowcost.domain.User;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.service.TicketService;
import com.epam.lowcost.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Виктория on 03.03.2016.
 */
public class ManageCommand extends ActionCommand {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException, IOException {
        String page = "/jsp/cabinet.jsp";
        TicketService ticketService = TicketService.getInstance();
        UserService userService = UserService.getInstance();
        HttpSession session = request.getSession();
        Long userId = Long.parseLong(session.getAttribute("userId").toString());
        User user = userService.findUserById(userId);
        List<Ticket> tickets = ticketService.getAllTicketsForUser(userId);
        request.setAttribute("tickets", tickets);
        request.setAttribute("user", user);

        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);

    }
}
