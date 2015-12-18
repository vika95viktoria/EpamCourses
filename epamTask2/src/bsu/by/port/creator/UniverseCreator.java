package bsu.by.port.creator;

import bsu.by.port.entity.Port;
import bsu.by.port.entity.Ship;
import bsu.by.port.entity.Universe;

import java.util.ArrayList;

/**
 * Created by Виктория on 06.12.2015.
 */
public class UniverseCreator {
    public static Universe createUniverse() {
        Universe universe = Universe.getUniverse();
        ArrayList<Port> ports = PortsCreator.createPorts();
        ArrayList<Ship> ships = ShipsCreator.createShips(ports);
        universe.setPorts(ports);
        universe.setShips(ships);
        return universe;
    }

}
