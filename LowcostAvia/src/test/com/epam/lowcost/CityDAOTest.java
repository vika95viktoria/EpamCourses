package com.epam.lowcost;

import com.epam.lowcost.dao.CityDAO;
import com.epam.lowcost.domain.City;
import com.epam.lowcost.exception.DAOException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Виктория on 20.03.2016.
 */
public class CityDAOTest {
    @Test
    public void testFindById() throws DAOException {
        CityDAO cityDAO = CityDAO.getInstance();
        City city = cityDAO.findEntityById(-5L);
        Assert.assertEquals(0, city.getId());
        Assert.assertEquals(null, city.getName());
    }
}
