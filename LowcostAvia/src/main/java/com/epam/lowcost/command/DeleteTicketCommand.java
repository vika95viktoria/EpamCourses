package com.epam.lowcost.command;

import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.service.FlightService;
import com.epam.lowcost.service.TicketService;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Виктория on 05.03.2016.
 */
public class DeleteTicketCommand extends ActionCommand {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        JSONObject info = new JSONObject(request.getParameter("ticketInfo"));
        Long ticketId = info.getLong("id");
        Long flightId = info.getLong("flightId");
        boolean actual = info.getBoolean("actual");
        boolean isBusiness = info.getBoolean("isBusiness");
        TicketService ticketService = TicketService.getInstance();
        ticketService.deleteTicket(ticketId);
        if (actual) {
            FlightService service = FlightService.getInstance();
            service.returnTicket(isBusiness, flightId);
        }

    }
}
