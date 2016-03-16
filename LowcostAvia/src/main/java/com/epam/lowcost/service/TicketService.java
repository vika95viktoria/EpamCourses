package com.epam.lowcost.service;

import com.epam.lowcost.dao.TicketDAO;
import com.epam.lowcost.domain.Ticket;
import com.epam.lowcost.domain.TicketModel;
import com.epam.lowcost.exception.DAOException;
import com.epam.lowcost.exception.ServiceException;

import java.util.List;

/**
 * Created by Виктория on 01.03.2016.
 */
public class TicketService {
    private static TicketService ticketService = new TicketService();
    private TicketDAO ticketDAO = TicketDAO.getInstance();

    private TicketService() {
    }

    public static TicketService getInstance() {
        return ticketService;
    }

    public void createTickets(List<TicketModel> models, Long userId) throws ServiceException {
        try {
            ticketDAO.createTickets(models, userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Ticket> getAllTicketsForUser(Long userId) throws ServiceException {
        List<Ticket> tickets;
        try {
            tickets = ticketDAO.findAllForUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return tickets;
    }

    public void deleteTicket(Long id) throws ServiceException {
        try {
            ticketDAO.remove(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void updateLuggageAndPriority(int priority, int luggage, Long id) throws ServiceException {
        try {
            ticketDAO.updateLuggageAndPriority(luggage, priority, id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
