package com.epam.lowcost.command;

import com.epam.lowcost.resource.ConfigurationManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.lowcost.util.CommandConstants.INDEX_PATH;
import static com.epam.lowcost.util.CommandConstants.PATH_PROPERTIES_FILE;

/**
 * Created by Виктория on 18.02.2016.
 */
public class LogoutCommand extends ActionCommand {
    /**
     * Destroy session and redirect to main page
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadProperties(PATH_PROPERTIES_FILE);
        String page = configurationManager.getProperty(INDEX_PATH);
        request.getSession().invalidate();
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);

    }
}
