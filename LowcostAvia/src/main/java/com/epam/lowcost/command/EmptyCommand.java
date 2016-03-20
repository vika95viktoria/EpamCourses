package com.epam.lowcost.command;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.lowcost.util.CommandConstants.INDEX_PATH;

/**
 * Created by Виктория on 18.02.2016.
 */
public class EmptyCommand extends ActionCommand {
    /**
     * Open start page
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        openPage(INDEX_PATH, request, response);
    }
}
