package com.epam.lowcost.command;


import static com.epam.lowcost.util.CommandConstants.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Виктория on 18.02.2016.
 */
public class EmptyCommand extends ActionCommand {

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        openPage(INDEX_PATH,request,response);
    }
}
