package bsu.by.port.entity;

import bsu.by.port.creator.ContainersCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Виктория on 06.12.2015.
 */
public class Storage {
    private final int MAX_CAPACITY = 100;
    private ArrayList<Container> containers;
    private ReentrantLock lock = new ReentrantLock();

    public Storage() {
        containers = new ArrayList<>(MAX_CAPACITY);
    }

    public Storage( int occupancy) {
        this.containers = new ArrayList<>(MAX_CAPACITY);
        if(occupancy>0) {
            this.containers.addAll(ContainersCreator.createContainers(occupancy));
        }
    }


    public List<Container> takeContainers(int numberOfContainers) {
        List<Container> containerList = new ArrayList<>();
        try {
            lock.lock();
            if (!containers.isEmpty()) {
                if (numberOfContainers >= containers.size()) {
                    containerList = containers.subList(0, (containers.size() - 1));
                    containers.removeAll(containerList);
                } else {
                    containerList = containers.subList(0, (numberOfContainers - 1));
                    containers.removeAll(containerList);
                }
            }
        }
        finally {
            lock.unlock();
        }
        return containerList;
    }

    public void putContainers(List<Container> containerList) {
        try {
            lock.lock();
            if (containerList.size() + containers.size() <= MAX_CAPACITY) {
                containers.addAll(containerList);
            } else {
                containers.addAll(containerList.subList(0, MAX_CAPACITY - containers.size()));
            }
        }
        finally {
            lock.unlock();
        }
    }

}
