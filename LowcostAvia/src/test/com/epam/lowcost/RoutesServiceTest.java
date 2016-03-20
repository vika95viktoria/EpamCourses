package com.epam.lowcost;

import com.epam.lowcost.dao.RoutesDAO;
import com.epam.lowcost.domain.Route;
import com.epam.lowcost.exception.DAOException;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.service.RoutesService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Виктория on 20.03.2016.
 */
public class RoutesServiceTest {
    @Test
    public void testFindAll() throws DAOException, ServiceException {
        RoutesService service = RoutesService.getInstance();
        RoutesDAO routesDAO = RoutesDAO.getInstance();
        List<Route> routes = service.getAllRoutes();
        List<Route> routeList = routesDAO.findAll();
        Assert.assertEquals(routeList.size(),routes.size());
        for(int i=0; i<routeList.size(); i++){
            Assert.assertEquals(routeList.get(i).getId(),routes.get(i).getId());
        }
    }
}
