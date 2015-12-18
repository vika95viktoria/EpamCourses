package bsu.by.port.main;


import bsu.by.port.action.UniverseStarter;
import bsu.by.port.creator.UniverseCreator;
import bsu.by.port.entity.Universe;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by Виктория on 06.12.2015.
 */
public class Main {
    static {
        PropertyConfigurator.configure("resources/log4j.properties");
    }

    public static void main(String[] args) {
        Universe universe = UniverseCreator.createUniverse();
        UniverseStarter.startUniverse(universe);
    }
}
