package com.epam.lowcost.resource;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Виктория on 18.02.2016.
 */
public class ConfigurationManager {
    private Properties prop = new Properties();
    private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    private static Logger logger = Logger.getLogger(ConfigurationManager.class);

    public void loadProperties(String filename) {
        try (InputStream input = classLoader.getResourceAsStream(filename)) {
            prop.load(input);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public String getProperty(String key) {
        return prop.getProperty((key));
    }
}
