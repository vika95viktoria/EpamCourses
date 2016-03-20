package com.epam.lowcost.controller;

import com.epam.lowcost.command.ActionCommand;
import com.epam.lowcost.factory.ActionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.lowcost.util.CommandConstants.PARAM_NAME_COMMAND;

/**
 * Created by Виктория on 20.02.2016.
 */
@WebServlet("/airepam")
public class LowcostServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActionFactory client = new ActionFactory();
        String action = request.getParameter(PARAM_NAME_COMMAND);
        ActionCommand command = client.defineGetCommand(action);
        command.execute(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActionFactory client = new ActionFactory();
        String action = request.getParameter(PARAM_NAME_COMMAND);
        ActionCommand command = client.definePostCommand(action);
        command.execute(request, response);
    }

}
