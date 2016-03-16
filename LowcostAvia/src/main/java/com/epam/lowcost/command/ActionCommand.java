package com.epam.lowcost.command;

import com.epam.lowcost.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Виктория on 18.02.2016.
 */
public abstract class ActionCommand {
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            action(request, response);
        } catch (IOException e) {

        } catch (ServiceException e) {

        } catch (ServletException e) {

        } catch (ParseException e) {

        }
    }

    protected abstract void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException, ServletException, ParseException;
}
