package com.epam.lowcost.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.lowcost.util.CommandConstants.*;

/**
 * Created by Виктория on 12.03.2016.
 */
public class ChangeLanguageCommand extends ActionCommand {
    /**
     * Switch the language
     *
     * @param request
     * @param response
     */
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) {
        String language = request.getParameter(PARAM_NAME_LANGUAGE);
        if (ATTRIBUTE_NAME_RUS.equals(language)) {
            request.getSession().setAttribute(ATTRIBUTE_NAME_LANGUAGE, RUS_LOCALE);
        } else {
            request.getSession().setAttribute(ATTRIBUTE_NAME_LANGUAGE, ENG_LOCALE);
        }
    }
}
