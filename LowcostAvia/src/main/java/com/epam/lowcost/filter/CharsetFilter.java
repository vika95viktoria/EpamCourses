package com.epam.lowcost.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Виктория on 07.03.2016.
 */
@WebFilter(urlPatterns = {"/*"},
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})
public class CharsetFilter implements Filter {
    private String encoding;

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String codeRequest = request.getCharacterEncoding();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String language = (String) session.getAttribute("language");
        if (language == null || language.isEmpty()) {
            session.setAttribute("language", "en_US");
        }
        request.setCharacterEncoding(encoding);
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding(encoding);
//        if (encoding != null && !encoding.equalsIgnoreCase(codeRequest)) {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
//        }
        chain.doFilter(request, response);
    }

    public void destroy() {
        encoding = null;
    }
}
