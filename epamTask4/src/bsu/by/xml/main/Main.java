package bsu.by.xml.main;

/**
 * Created by Виктория on 22.12.2015.
 */

import bsu.by.xml.action.SimpleTransform;
import bsu.by.xml.action.ValidatorSAXXSD;
import org.apache.log4j.PropertyConfigurator;

public class Main {
    static {
        PropertyConfigurator.configure("resources/log4j.properties");
    }
    public static void main(String [] args) {
        String fileName = "data/tariffs.xml";
        String schemaName = "data/tariffs.xsd";
        ValidatorSAXXSD.validate(fileName,schemaName);
        String fileName2 = "data/newtariffs.txt";
       // SimpleTransform.transform(fileName2);
    }
}
