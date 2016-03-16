package com.epam.lowcost.command;

import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.service.CardService;
import com.epam.lowcost.service.TicketService;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Виктория on 05.03.2016.
 */
public class EditTicketCommand extends ActionCommand {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        HttpSession session = request.getSession();
        Long userId = Long.parseLong(session.getAttribute("userId").toString());
        JSONObject info = new JSONObject(request.getParameter("ticketInfo"));
        Long ticketId = info.getLong("ticketId");
        int luggage = info.getInt("luggage");
        int priority = info.getInt("priority");
        boolean luggageChange = info.getBoolean("luggageChange");
        boolean priorChange = info.getBoolean("priorChange");
        CardService cardService = CardService.getInstance();

        ServiceMessage message = cardService.editTicket(priority, luggage, userId, luggageChange, priorChange);
        boolean buy = false;
        if (message.equals(ServiceMessage.OKEDIT)) {
            buy = true;
        }
        if (buy) {
            TicketService ticketService = TicketService.getInstance();
            ticketService.updateLuggageAndPriority(priority, luggage, ticketId);
        }
        response.getWriter().print(message.getValue());


    }
}
