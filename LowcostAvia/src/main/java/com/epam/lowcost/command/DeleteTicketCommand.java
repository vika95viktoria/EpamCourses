package com.epam.lowcost.command;

import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.service.FlightService;
import com.epam.lowcost.service.TicketService;
import static com.epam.lowcost.util.CommandConstants.*;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Виктория on 05.03.2016.
 */
public class DeleteTicketCommand extends ActionCommand {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        JSONObject info = new JSONObject(request.getParameter(PARAM_NAME_TICKET_INFO));
        Long ticketId = info.getLong(PARAM_NAME_ID);
        Long flightId = info.getLong(PARAM_NAME_FLIGHT_ID);
        boolean actual = info.getBoolean(PARAM_NAME_ACTUAL);
        boolean isBusiness = info.getBoolean(PARAM_NAME_IS_BUSINESS);
        TicketService ticketService = TicketService.getInstance();
        ticketService.deleteTicket(ticketId);
        if (actual) {
            FlightService service = FlightService.getInstance();
            service.returnTicket(isBusiness, flightId);
        }

    }
}
