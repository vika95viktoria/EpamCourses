package bsu.by.port.creator;

import bsu.by.port.entity.Container;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Виктория on 06.12.2015.
 */
public class ContainersCreator {
    public static List<Container> createContainers(int numberOfContainers) {
        List<Container> containers = new ArrayList<>(numberOfContainers);
        for (int i = 0; i < numberOfContainers; i++) {
            containers.add(new Container(new Random(100).nextInt(500)));
        }
        return containers;
    }
}
