package com.epam.lowcost.command;

import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.exception.ValidationException;
import com.epam.lowcost.resource.ConfigurationManager;
import static com.epam.lowcost.util.CommandConstants.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Виктория on 18.02.2016.
 */

public abstract class ActionCommand {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadProperties(PATH_PROPERTIES_FILE);
        try {
            action(request, response);
        } catch (IOException | ServiceException |ServletException |ParseException e) {
            String page = configurationManager.getProperty(ERROR_500_PATH);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } catch (ValidationException e){
            String page = configurationManager.getProperty(ERROR_400_PATH);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }
    protected void openPage(String pageName, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadProperties(PATH_PROPERTIES_FILE);
        String page = configurationManager.getProperty(pageName);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
    protected abstract void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException, ServletException, ParseException, ValidationException;
}
