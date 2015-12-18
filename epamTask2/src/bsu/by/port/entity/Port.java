package bsu.by.port.entity;


import org.apache.log4j.Logger;

import java.util.concurrent.Semaphore;

/**
 * Created by Виктория on 06.12.2015.
 */
public class Port {
    private String name;
    private int numberOfBerths;
    private Storage storage;
    private Semaphore semaphore;
    private static Logger logger = Logger.getLogger(Port.class);

    public Port() {
    }

    public Port(String name, int numberOfBerths, Storage storage) {
        this.name = name;
        this.numberOfBerths = numberOfBerths;
        this.storage = storage;
        this.semaphore = new Semaphore(numberOfBerths, true);
    }


    public void entranceToPort(int id) {
        try {
            semaphore.acquire();
            logger.info("Ship number " + id + " moored in port " + name);
        } catch (InterruptedException e) {
            logger.warn(e);
        }

    }

    public void leftPort(int id) {
        logger.info("Ship number " + id + " left port " + name);
        semaphore.release();
    }


    public Storage getStorage() {
        return storage;
    }

    public String getName() {
        return name;
    }
}
