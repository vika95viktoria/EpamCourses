package com.epam.lowcost.command;

import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.domain.TicketModel;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.service.FlightService;
import com.epam.lowcost.service.TicketService;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.lowcost.util.CommandConstants.*;

/**
 * Created by Виктория on 05.03.2016.
 */
public class TicketCommand extends ActionCommand {
    /**
     * Parse json with parameters of ticket, check if user has enough money and how many seats left. If everything is ok, process buy request
     * and return the message (success/error)
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServiceException
     */

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException {
        HttpSession session = request.getSession();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = request.getParameter(PARAM_NAME_TICKET_INFO);
        List<TicketModel> ticketModels;

        ticketModels = mapper.readValue(jsonInString, new TypeReference<List<TicketModel>>() {});
        Long userId = Long.parseLong(session.getAttribute(ATTRIBUTE_NAME_USER_ID).toString());
        for (TicketModel model : ticketModels) {
            if (model.getLuggageCount() == 0) {
                model.setLuggage(0);
            }
        }
        boolean buy = false;
        FlightService flightService = FlightService.getInstance();
        ServiceMessage message = flightService.buyTicket(userId, ticketModels);
        if (message.equals(ServiceMessage.OK_BUY)) {
            buy = true;
        }
        if (buy) {
            TicketService ticketService = TicketService.getInstance();
            ticketService.createTickets(ticketModels, userId);
        }
        String language = (String) session.getAttribute(ATTRIBUTE_NAME_LANGUAGE);
        response.getWriter().print(message.getValue(language));

    }
}
