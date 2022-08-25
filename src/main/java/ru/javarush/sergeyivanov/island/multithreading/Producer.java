package ru.javarush.sergeyivanov.island.multithreading;

import ru.javarush.sergeyivanov.island.content_of_island.Nature;
import ru.javarush.sergeyivanov.island.content_of_island.fauna.Animal;
import ru.javarush.sergeyivanov.island.content_of_island.field.Location;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {
    private BlockingQueue<Queue<? extends Nature>> queue;
    Location currentLocation;

    public Producer(BlockingQueue<Queue<? extends Nature>> queue, Location currentLocation) {
        this.queue = queue;
        this.currentLocation = currentLocation;
    }

    public void run() {

        for (Map.Entry<Class<? extends Nature>, Queue<? extends Nature>> entry :
                currentLocation.getMapQueuesNatureObj().entrySet())
        {
            Class<? extends Nature> key = entry.getKey();
            if (Animal.class.isAssignableFrom(key)) {

                try {
                    queue.put(entry.getValue());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}