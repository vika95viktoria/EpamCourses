package bsu.by.port.action;

import bsu.by.port.entity.Ship;
import bsu.by.port.entity.Universe;

/**
 * Created by Виктория on 07.12.2015.
 */
public class UniverseStarter {
    public static void startUniverse(Universe universe) {
        for (Ship ship : universe.getShips()) {
            Thread thread = new Thread(ship);
            thread.start();
        }
    }
}
