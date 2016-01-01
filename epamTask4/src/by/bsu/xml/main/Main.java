package by.bsu.xml.main;

/**
 * Created by Виктория on 22.12.2015.
 */

import by.bsu.xml.action.ValidatorSAXXSD;
import org.apache.log4j.PropertyConfigurator;

public class Main {
    static {
        PropertyConfigurator.configure("resources/log4j.properties");
    }
    public static void main(String [] args) {
        String fileName = "data/tariffs.xml";
        String schemaName = "data/tariffs.xsd";
        ValidatorSAXXSD.validate(fileName,schemaName);
    }
}
