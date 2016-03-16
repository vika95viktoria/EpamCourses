package com.epam.lowcost.command;


import java.util.HashMap;

/**
 * Created by Виктория on 18.02.2016.
 */
public class ActionFactory {
    private HashMap<String, ActionCommand> getCommandHashMap = new HashMap<>();
    private HashMap<String, ActionCommand> postCommandHashMap = new HashMap<>();

    public ActionFactory() {
        postCommandHashMap.put("addCity", new AddCityCommand());
        postCommandHashMap.put("addRoute", new AddRouteCommand());
        postCommandHashMap.put("cityTo", new CityToCommand());
        postCommandHashMap.put("deleteFlights", new DeleteFlightsCommand());
        postCommandHashMap.put("deleteTicket", new DeleteTicketCommand());
        postCommandHashMap.put("editFlight", new EditFlightCommand());
        postCommandHashMap.put("editTicket", new EditTicketCommand());
        getCommandHashMap.put("flights", new FlightsCommand());
        postCommandHashMap.put("flights", new FlightsCommand());
        postCommandHashMap.put("login", new LoginCommand());
        getCommandHashMap.put("logout", new LogoutCommand());
        getCommandHashMap.put("manage", new ManageCommand());
        getCommandHashMap.put("openCreator", new OpenCreatorCommand());
        postCommandHashMap.put("register", new RegisterCommand());
        getCommandHashMap.put("routes", new RoutesCommand());
        postCommandHashMap.put("ticket", new TicketCommand());
        postCommandHashMap.put("changeLanguage", new ChangeLanguageCommand());
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
