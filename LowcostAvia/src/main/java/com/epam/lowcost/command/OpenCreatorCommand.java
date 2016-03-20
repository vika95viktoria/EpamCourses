package com.epam.lowcost.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.lowcost.util.CommandConstants.CREATE_PAGE_PATH;

/**
 * Created by Виктория on 05.03.2016.
 */
public class OpenCreatorCommand extends ActionCommand {
    /**
     * Open creator page for admin
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        openPage(CREATE_PAGE_PATH, request, response);
    }
}
