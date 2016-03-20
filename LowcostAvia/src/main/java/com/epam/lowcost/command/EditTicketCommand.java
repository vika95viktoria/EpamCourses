package com.epam.lowcost.command;

import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.service.CardService;
import com.epam.lowcost.service.TicketService;
import static com.epam.lowcost.util.CommandConstants.*;
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
        Long userId = Long.parseLong(session.getAttribute(ATTRIBUTE_NAME_USER_ID).toString());
        JSONObject info = new JSONObject(request.getParameter(PARAM_NAME_TICKET_INFO));
        Long ticketId = info.getLong(PARAM_NAME_TICKET_ID);
        int luggage = info.getInt(PARAM_NAME_LUGGAGE);
        int priority = info.getInt(PARAM_NAME_PRIORITY);
        boolean luggageChange = info.getBoolean(PARAM_NAME_LUGGAGE_CHANGE);
        boolean priorChange = info.getBoolean(PARAM_NAME_PRIORITY_CHANGE);
        CardService cardService = CardService.getInstance();

        ServiceMessage message = cardService.editTicket(priority, luggage, userId, luggageChange, priorChange);
        boolean buy = false;
        if (message.equals(ServiceMessage.OK_EDIT)) {
            buy = true;
        }
        if (buy) {
            TicketService ticketService = TicketService.getInstance();
            ticketService.updateLuggageAndPriority(priority, luggage, ticketId);
        }
        String language = (String)session.getAttribute(ATTRIBUTE_NAME_LANGUAGE);
        response.getWriter().print(message.getValue(language));


    }
}
