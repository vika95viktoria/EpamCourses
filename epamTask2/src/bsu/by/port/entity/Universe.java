package bsu.by.port.entity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Виктория on 06.12.2015.
 */
public class Universe {
    private static Universe universe;
    private static AtomicBoolean created = new AtomicBoolean(false);
    private List<Port> ports;
    private List<Ship> ships;

    private Universe() {
    }

    public static Universe getUniverse() {
        if (!created.get()) {
            universe = new Universe();
            created.set(true);
        }
        return universe;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }
}
