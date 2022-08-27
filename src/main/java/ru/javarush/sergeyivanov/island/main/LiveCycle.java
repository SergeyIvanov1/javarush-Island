package ru.javarush.sergeyivanov.island.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.javarush.sergeyivanov.island.content_of_island.fauna.Animal;
import ru.javarush.sergeyivanov.island.content_of_island.field.Island;
import ru.javarush.sergeyivanov.island.content_of_island.field.Location;
import ru.javarush.sergeyivanov.island.content_of_island.Nature;
import ru.javarush.sergeyivanov.island.multithreading.Consumer;
import ru.javarush.sergeyivanov.island.multithreading.Producer;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

public class LiveCycle {
    static final Logger rootLogger = LogManager.getRootLogger();
    private static final int AMOUNT_THREADS = 15;

    public void launch() {
        BlockingQueue<Queue<? extends Nature>> synchronousQueue = new SynchronousQueue<>();

        for (int i = 0; i < Island.getInstance().getWidthField(); i++) {
            for (int j = 0; j < Island.getInstance().getHeightField(); j++) {
                Location currentLocation = Island.getInstance().getField()[i][j];

                Producer producer = new Producer(synchronousQueue, currentLocation);
                producer.start();

                Consumer[] consumers = new Consumer[AMOUNT_THREADS];
                for (int k = 0; k < AMOUNT_THREADS; k++) {
                    consumers[k] = new Consumer(synchronousQueue);
                    consumers[k].start();
                }
            }
        }
    }

    public void repeatCycle(int amount)  {
        for (int i = 0; i < amount; i++) {
            rootLogger.debug("**************** Launched cycle № " + i + " *******************");
            launch();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            updateCycle();
            Statistic.calculateAmountAnimals();
            Statistic.printParamCurrentCycle();
        }
        System.out.println("Detailed statistic are reflected in the log");
    }

    public void updateCycle() {
        ExecutorService serviceOfPlantGrowth = Executors.newWorkStealingPool();
        for (int i = 0; i < Island.getInstance().getWidthField(); i++) {
            for (int j = 0; j < Island.getInstance().getHeightField(); j++) {
                Location currentLocation = Island.getInstance().getField()[i][j];

                for (Map.Entry<Class<? extends Nature>, Queue<? extends Nature>> entry :
                        currentLocation.getMapQueuesNatureObj().entrySet()) {

                    if (Animal.class.isAssignableFrom(entry.getKey())) {
                        for (Nature object : entry.getValue()) {
                            Animal animal = (Animal) object;
                            animal.updateParamForNewCycle();
                        }
                    }
                }
                serviceOfPlantGrowth.submit(currentLocation);
            }
        }
        try {
            serviceOfPlantGrowth.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
