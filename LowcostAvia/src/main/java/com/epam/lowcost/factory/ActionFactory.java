package com.epam.lowcost.factory;


import com.epam.lowcost.command.*;
import static com.epam.lowcost.util.Commands.*;
import java.util.HashMap;

/**
 * Created by Виктория on 18.02.2016.
 */
public class ActionFactory {
    private HashMap<String, ActionCommand> getCommandHashMap = new HashMap<>();
    private HashMap<String, ActionCommand> postCommandHashMap = new HashMap<>();

    public ActionFactory() {
        postCommandHashMap.put(ADD_CITY_COMMAND, new AddCityCommand());
        postCommandHashMap.put(ADD_ROUTE_COMMAND, new AddRouteCommand());
        postCommandHashMap.put(CITY_TO_COMMAND, new CityToCommand());
        postCommandHashMap.put(DELETE_FLIGHTS_COMMAND, new DeleteFlightsCommand());
        postCommandHashMap.put(DELETE_TICKET_COMMAND, new DeleteTicketCommand());
        postCommandHashMap.put(EDIT_FLIGHT_COMMAND, new EditFlightCommand());
        postCommandHashMap.put(EDIT_TICKET_COMMAND, new EditTicketCommand());
        getCommandHashMap.put(FLIGHTS_COMMAND, new FlightsCommand());
        postCommandHashMap.put(FLIGHTS_COMMAND, new FlightsCommand());
        postCommandHashMap.put(LOGIN_COMMAND, new LoginCommand());
        getCommandHashMap.put(LOGOUT_COMMAND, new LogoutCommand());
        getCommandHashMap.put(MANAGE_COMMAND, new ManageCommand());
        getCommandHashMap.put(OPEN_CREATOR_COMMAND, new OpenCreatorCommand());
        postCommandHashMap.put(REGISTER_COMMAND, new RegisterCommand());
        getCommandHashMap.put(ROUTES_COMMAND, new RoutesCommand());
        postCommandHashMap.put(TICKET_COMMAND, new TicketCommand());
        postCommandHashMap.put(CHANGE_LANGUAGE_COMMAND, new ChangeLanguageCommand());
    }

    public ActionCommand defineGetCommand(String action) {
        ActionCommand current = getCommandHashMap.get(action);
        if (current == null) {
            current = new EmptyCommand();
        }
        return current;
    }

    public ActionCommand definePostCommand(String action) {
        ActionCommand current = postCommandHashMap.get(action);
        if (current == null) {
            current = new EmptyCommand();
        }
        return current;
    }
}
