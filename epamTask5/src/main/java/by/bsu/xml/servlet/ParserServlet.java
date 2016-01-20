package by.bsu.xml.servlet;



import by.bsu.xml.action.TariffMarshal;
import by.bsu.xml.builder.TariffsDOMBuilder;
import by.bsu.xml.builder.TariffsSAXBuilder;
import by.bsu.xml.builder.TariffsStAXBuilder;
import by.bsu.xml.entity.Tariff;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Виктория on 13.01.2016.
 */
@WebServlet("/parser")
public class ParserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Tariff> tariffs = new ArrayList<>();
        String action = request.getParameter("button");
        if("Parse".equals(action)) {
            String type = request.getParameter("radios");
            if ("1".equals(type)) {
                TariffsDOMBuilder tariffsDOMBuilder = new TariffsDOMBuilder();
                tariffsDOMBuilder.buildTariffs("file:" + getServletContext().getRealPath("WEB-INF/classes/data/tariffs.xml"));
                tariffs = tariffsDOMBuilder.getTariffs();
            } else if ("2".equals(type)) {
                TariffsSAXBuilder tariffsSAXBuilder = new TariffsSAXBuilder();
                tariffsSAXBuilder.buildTariffs("file:" + getServletContext().getRealPath("WEB-INF/classes/data/tariffs.xml"));
                tariffs = tariffsSAXBuilder.getTariffs();
            } else if ("3".equals(type)) {
                TariffsStAXBuilder tariffsStAXBuilder = new TariffsStAXBuilder();
                tariffsStAXBuilder.buildTariffs(getServletContext().getRealPath("WEB-INF/classes/data/tariffs.xml"));
                tariffs = tariffsStAXBuilder.getTariffs();
            }
            request.setAttribute("tariffs", tariffs);
            request.getRequestDispatcher("WEB-INF/jsp/result.jsp").forward(request, response);
        }
        else if("Marshall".equals(action)){
            TariffMarshal.marshalTariff(getServletContext().getRealPath("WEB-INF/classes/data/tariffs_marsh.xml"));
            response.sendRedirect("index.jsp");
        }

    }
}
