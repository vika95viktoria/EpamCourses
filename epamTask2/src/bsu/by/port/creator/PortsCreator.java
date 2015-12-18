package bsu.by.port.creator;

import bsu.by.port.entity.Port;
import bsu.by.port.entity.Storage;

import java.util.ArrayList;

/**
 * Created by Виктория on 07.12.2015.
 */
public class PortsCreator {
    public static ArrayList<Port> createPorts() {
        Storage storage1 = new Storage();
        Storage storage2 = new Storage( 5);
        ArrayList<Port> ports = new ArrayList<>();
        ports.add(new Port("Rotterdam", 2, storage1));
        ports.add(new Port("Los-Angeles", 1, storage2));
        return ports;
    }
}
