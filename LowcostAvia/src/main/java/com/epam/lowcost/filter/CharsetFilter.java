package com.epam.lowcost.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.lowcost.util.CommandConstants.*;

/**
 * Created by Виктория on 07.03.2016.
 */
@WebFilter(urlPatterns = {"/*"},
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})
public class CharsetFilter implements Filter {
    private String encoding;

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter(PARAM_NAME_ENCODING);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String language = (String) session.getAttribute(ATTRIBUTE_NAME_LANGUAGE);
        if (language == null || language.isEmpty()) {
            session.setAttribute(ATTRIBUTE_NAME_LANGUAGE, ENG_LOCALE);
        }
        request.setCharacterEncoding(encoding);
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding(encoding);
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    public void destroy() {
        encoding = null;
    }
}
