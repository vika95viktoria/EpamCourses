package bsu.by.port.creator;

import bsu.by.port.entity.Port;
import bsu.by.port.entity.Ship;

import java.util.ArrayList;

/**
 * Created by Виктория on 07.12.2015.
 */
public class ShipsCreator {
    public static ArrayList<Ship> createShips(ArrayList<Port> ports) {
        ArrayList<Ship> ships = new ArrayList<>();
        for(int i=0; i<5; i++){
            ships.add(new Ship(i+1, 10*(i+1), ports));
        }
        return ships;
    }
}
