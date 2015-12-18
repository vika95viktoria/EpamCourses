package bsu.by.port.entity;


import bsu.by.port.creator.ContainersCreator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Виктория on 06.12.2015.
 */
public class Ship implements Runnable {
    private int shipId;
    private final int MAX_CAPACITY=100;
    private List<Container> containers = new ArrayList<>();
    private ArrayList<Port> availablePorts = new ArrayList<>();
    private static Logger logger = Logger.getLogger(Ship.class);

    public Ship(int shipId) {
        this.shipId = shipId;
    }

    public Ship() {
    }

    public Ship(int shipId, int capacity, ArrayList<Port> availablePorts) {
        this.shipId = shipId;
        this.availablePorts = availablePorts;
        this.containers = ContainersCreator.createContainers(capacity);
    }

    private Port choosePort() {
        Random random = new Random();
        int portNumber = random.nextInt(availablePorts.size());
        Port port = availablePorts.get(portNumber);
        return port;
    }

    private void serviceInPort(Port port) {
        if (containers.isEmpty()) {
            containers = port.getStorage().takeContainers(MAX_CAPACITY);
            logger.info("Ship number " + shipId + " loaded in " + port.getName());
        } else {
            port.getStorage().putContainers(containers);
            containers.clear();
            logger.info("Ship number " + shipId + " unloaded in " + port.getName());
        }
    }

    private void goAway(Port port) {
        try {
            Thread.sleep(new Random(100).nextInt(500));
            logger.info("Ship number " + shipId + " go away from port " + port.getName());
            Thread.sleep(new Random(100).nextInt(1000));
        } catch (InterruptedException e) {
            logger.warn(e);
        }

    }

    public void run() {
        for (int i = 0; i < 2; i++) {
            Port port = choosePort();
            logger.info("Ship number " + shipId + " go to port " + port.getName());
            port.entranceToPort(shipId);
            serviceInPort(port);
            port.leftPort(shipId);
            goAway(port);
        }
    }

}
