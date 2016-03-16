package com.epam.lowcost.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Виктория on 12.03.2016.
 */
public class ChangeLanguageCommand extends ActionCommand {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("lang");
        if ("rus".equals(name)) {
            request.getSession().setAttribute("language", "ru_RU");
        } else {
            request.getSession().setAttribute("language", "en_US");
        }
    }
}
